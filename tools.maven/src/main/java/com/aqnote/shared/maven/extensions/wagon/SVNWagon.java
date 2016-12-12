/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.maven.extensions.wagon;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.wagon.AbstractWagon;
import org.apache.maven.wagon.ConnectionException;
import org.apache.maven.wagon.ResourceDoesNotExistException;
import org.apache.maven.wagon.TransferFailedException;
import org.apache.maven.wagon.authentication.AuthenticationException;
import org.apache.maven.wagon.authentication.AuthenticationInfo;
import org.apache.maven.wagon.authorization.AuthorizationException;
import org.apache.maven.wagon.events.TransferEvent;
import org.apache.maven.wagon.resource.Resource;
import org.codehaus.plexus.util.IOUtil;
import org.tmatesoft.svn.core.SVNAuthenticationException;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.ISVNEditor;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.io.diff.SVNDeltaGenerator;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author madding.lip
 */
public class SVNWagon extends AbstractWagon {

    private static final String lockFileName = ".lock";

    public static final String PROPERTY_DEPLOY_REPOSITORY = "deploy.repository";

    public static final String PROPERTY_COMMIT_MESSAGE    = "commit.message";

    public static final String PROPERTY_DEPLOY_DIRECTLY   = "deploy.directly";

    static {
        DAVRepositoryFactory.setup();
        FSRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
    }

    /**
     * Locale repository where artifacts deployed to, default ${user.home}/.m2/svn.
     * -Ddeploy.repository.
     */
    private File            deployRepository;
    private SVNURL          repositoryRoot;
    private String          repositoryPath;
    private SVNRepository   readRepository;
    private SVNRepository   writeRepository;
    private ISVNEditor      writeEditor;
    private Set<String>     addedEntries;
    private boolean         deployDirectly = Boolean.getBoolean( PROPERTY_DEPLOY_DIRECTLY );
    private boolean         writeAttempted;
    private boolean         writeSuccessful;
    private String          commitMessage  = System.getProperty( PROPERTY_COMMIT_MESSAGE );

    public SVNWagon() {

        String deployRepo = System.getProperty( PROPERTY_DEPLOY_REPOSITORY );
        if ( deployRepo != null ) {
            deployRepository = new File( deployRepo );
        }

        if ( deployRepository == null ) {
            deployRepository = new File( System.getProperty( "user.home" ), ".m2/svn" );
        }

        try {
            deployRepository = deployRepository.getCanonicalFile();
        } catch ( IOException e ) {
            throw new RuntimeException( e.getMessage(), e );
        }
    }

    @Override
    protected void openConnectionInternal()
            throws ConnectionException, AuthenticationException {
        String svnRepositoryURL = getRepository().getUrl().toLowerCase();
        if ( !svnRepositoryURL.startsWith( "svn:" ) ) {
            throw new AssertionError( "unexpected wagon protocol: " + svnRepositoryURL );
        }

        try {
            SVNURL wagonRepositoryRoot = SVNURL.parseURIDecoded( svnRepositoryURL.substring( "svn:".length() ) );
            SVNRepository svnRepository = SVNRepositoryFactory.create( wagonRepositoryRoot );
            svnRepository.setAuthenticationManager( createAuthenticationManager() );
            repositoryRoot = svnRepository.getRepositoryRoot( true );
            svnRepository.closeSession();
            repositoryPath = wagonRepositoryRoot.getPath().substring( repositoryRoot.getPath().length() );
            if ( repositoryPath.startsWith( "/" ) ) {
                repositoryPath = repositoryPath.substring( 1 );
            }
            if ( repositoryPath.endsWith( "/" ) ) {
                repositoryPath = repositoryPath.substring( 0, repositoryPath.length() - 1 );
            }
        } catch ( SVNAuthenticationException e ) {
            throw new AuthenticationException( e.getMessage(), e );
        } catch ( SVNException e ) {
            throw new ConnectionException( e.getMessage(), e );
        }
    }

    @Override
    protected void closeConnection()
            throws ConnectionException {
        repositoryRoot = null;
        repositoryPath = null;
        try {
            commitWriteSession();
        } catch ( SVNException e ) {
            throw new ConnectionException( e.getMessage(), e );
        } finally {
            if ( readRepository != null ) {
                try {
                    readRepository.closeSession();
                } catch (SVNException e) {
                } finally {
                    readRepository = null;
                }
                
            }
            if ( writeRepository != null ) {
                try {
                    writeRepository.closeSession();
                } catch (SVNException e) {
                } finally {
                    writeRepository = null;
                }
                
            }
        }
    }

    @Override
    public boolean resourceExists( String repositoryResourceName ) throws TransferFailedException, AuthorizationException {
        String repositoryResourcePath = getResourcePath( repositoryResourceName );
        try {
            SVNNodeKind repositoryResourceKind = getReadRepository().checkPath( repositoryResourcePath, -1 );
            return SVNNodeKind.NONE.compareTo( repositoryResourceKind ) != 0;
        } catch ( SVNAuthenticationException e ) {
            throw new AuthorizationException( e.getMessage(), e );
        } catch ( SVNException e ) {
            throw new TransferFailedException( e.getMessage(), e );
        }
    }

    public void get( String repositoryResourceName, File localFile )
            throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
        String repositoryResourcePath = getResourcePath( repositoryResourceName );
        try {
            SVNNodeKind repositoryResourceKind = getReadRepository().checkPath( repositoryResourcePath, -1 );
            if ( SVNNodeKind.FILE.equals( repositoryResourceKind ) ) {
                getInternal( repositoryResourcePath, localFile, new Resource( repositoryResourceName ) );
            } else if ( SVNNodeKind.NONE.equals( repositoryResourceKind ) ) {
                throw new ResourceDoesNotExistException( repositoryResourceName + " does not exist" );
            } else {
                throw new ResourceDoesNotExistException( repositoryResourceName + " is not a file" );
            }
        } catch ( FileNotFoundException e ) {
            throw new TransferFailedException( e.getMessage(), e );
        } catch ( SVNAuthenticationException e ) {
            throw new AuthorizationException( e.getMessage(), e );
        } catch ( SVNException e ) {
            throw new TransferFailedException( e.getMessage(), e );
        }
    }

    public boolean getIfNewer( String repositoryResourceName, File localFile, long timestamp )
            throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {

        String repositoryResourcePath = getResourcePath( repositoryResourceName );
        try {
            SVNDirEntry repositoryResourceEntry = getReadRepository().info( repositoryResourcePath, -1 );
            if ( repositoryResourceEntry == null ) {
                throw new ResourceDoesNotExistException( repositoryResourceName + " does not exist" );
            } else if ( SVNNodeKind.FILE.equals( repositoryResourceEntry.getKind() ) ) {
                if ( repositoryResourceEntry.getDate().getTime() <= timestamp ) {
                    fireGetInitiated( new Resource( repositoryResourceName ), localFile ); // expected by the org.apache.maven.wagon.WagonTestCase
                    return false;
                } else {
                    getInternal( repositoryResourcePath, localFile, new Resource( repositoryResourceName ) );
                    return true;
                }
            } else {
                throw new ResourceDoesNotExistException( repositoryResourceName + " is not a file" );
            }
        } catch ( FileNotFoundException e ) {
            throw new TransferFailedException( e.getMessage(), e );
        } catch ( SVNAuthenticationException e ) {
            throw new AuthorizationException( e.getMessage(), e );
        } catch ( SVNException e ) {
            throw new TransferFailedException( e.getMessage(), e );
        }
    }

    public void put( File source, String destination )
            throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {

        if ( deployDirectly ) {
            putDirectly( source, destination );
        } else {

            // todo how to check directory if already exists

            Resource resource = new Resource( destination );
            resource.setContentLength( source.length() );
            resource.setLastModified( source.lastModified() );
            firePutInitiated( resource, source );

            // 加写独占锁
            File lockFile = new File( deployRepository, lockFileName );
            if ( !deployRepository.isDirectory() ) {
                deployRepository.mkdirs();
            }

            FileOutputStream fos;
            FileLock lock;
            try {
                fos = new FileOutputStream( lockFile, true );
            } catch ( FileNotFoundException e ) {
                throw new TransferFailedException( e.getMessage(), e );
            }

            FileChannel channel = fos.getChannel();
            try {
                lock = channel.lock( 0L, Long.MAX_VALUE, false );
            } catch ( IOException e ) {
                throw new TransferFailedException( e.getMessage(), e );
            }

            try {
                File destFile = new File( deployRepository, destination );
                if ( destFile.exists() ) {
                    destFile.delete();
                }
                firePutStarted( resource, source );
                FileUtils.copyFile( source, destFile );
            } catch ( IOException e ) {
                fireTransferError( resource, e, TransferEvent.REQUEST_PUT );
                throw new TransferFailedException( e.getMessage(), e );
            } finally {
                // 释放锁
                try {
                    lock.release();
                } catch ( IOException e ) {
                    // ignore
                }
                IOUtil.close( fos );
            }

            postProcessListeners( resource, source, TransferEvent.REQUEST_PUT );
            firePutCompleted( resource, source );
        }
    }

    private void putDirectly( File localFile, String repositoryResourceName ) throws TransferFailedException, AuthorizationException {

        String repositoryResourcePath = getResourcePath( repositoryResourceName );
        try {
            ISVNEditor editor = getWriteEditor( getCommitMessage() );
            String[] pathComponents = repositoryResourcePath.split( "/" );
            openDirectoriesInternal( editor, pathComponents, 0, pathComponents.length - 1 );
            putFileInternal( localFile, repositoryResourcePath, new Resource( repositoryResourceName ) );
            closeDirectoriesInternal( editor, pathComponents.length - 1 );
        } catch ( FileNotFoundException e ) {
            writeSuccessful = false;
            throw new TransferFailedException( e.getMessage(), e );
        } catch ( SVNAuthenticationException e ) {
            writeSuccessful = false;
            throw new AuthorizationException( e.getMessage(), e );
        } catch ( SVNException e ) {
            writeSuccessful = false;
            throw new TransferFailedException( e.getMessage(), e );
        } catch ( TransferFailedException e ) {
            writeSuccessful = false;
            throw e;
        }
    }

    @Override
    public boolean supportsDirectoryCopy() {
        return false;
    }

    @Override
    public void putDirectory( File sourceDirectory, String destinationDirectory )
            throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {
        // todo
    }

    @Override
    public List<String> getFileList( String repositoryDirectoryName )
            throws TransferFailedException, ResourceDoesNotExistException, AuthorizationException {

        String repositoryDirectoryPath = getResourcePath( repositoryDirectoryName );
        try {
            SVNNodeKind repositoryDirectoryKind = getReadRepository().checkPath( repositoryDirectoryPath, -1 );
            if ( SVNNodeKind.DIR.equals( repositoryDirectoryKind ) ) {
                @SuppressWarnings( "unchecked" )
                Collection<SVNDirEntry> repositoryDirectoryEntries = getReadRepository().getDir( repositoryDirectoryPath, -1, null, ( Collection<SVNDirEntry> ) null );
                List<String> list = new ArrayList<String>();
                for ( SVNDirEntry entry : repositoryDirectoryEntries ) {
                    if ( SVNNodeKind.DIR.equals( entry.getKind() ) ) {
                        list.add( entry.getRelativePath() + '/' );
                    } else {
                        list.add( entry.getRelativePath() );
                    }
                }
                return list;
            } else if ( SVNNodeKind.NONE.equals( repositoryDirectoryKind ) ) {
                throw new ResourceDoesNotExistException( repositoryDirectoryName + " does not exist" );
            } else {
                throw new ResourceDoesNotExistException( repositoryDirectoryName + " is not a directory" );
            }
        } catch ( SVNAuthenticationException e ) {
            throw new AuthorizationException( e.getMessage(), e );
        } catch ( SVNException e ) {
            throw new TransferFailedException( e.getMessage(), e );
        }
    }

    private String getResourcePath( String repositoryResourceName ) {
        if ( ".".equals( repositoryResourceName ) ) {
            return repositoryPath;
        }
        if ( repositoryResourceName.startsWith( "." ) ) {
            repositoryResourceName = repositoryResourceName.substring( 1 );
        }
        if ( repositoryResourceName.startsWith( "/" ) || repositoryResourceName.startsWith( "\\" ) ) {
            repositoryResourceName = repositoryResourceName.substring( 1 );
        }
        return repositoryPath.length() == 0 ? repositoryResourceName : repositoryPath + '/' + repositoryResourceName;
    }

    private SVNRepository getReadRepository() throws SVNException {
        if ( readRepository == null ) {
            readRepository = SVNRepositoryFactory.create( repositoryRoot );
            readRepository.setAuthenticationManager( createAuthenticationManager() );
        }
        return readRepository;
    }

    private ISVNAuthenticationManager createAuthenticationManager() {
        AuthenticationInfo authInfo = getAuthenticationInfo();
        if ( authInfo.getUserName() == null
                || authInfo.getPassphrase() == null ) {
            return new BasicAuthenticationManager(
                    authInfo.getUserName(), authInfo.getPassword() );
        } else {
            return SVNWCUtil.createDefaultAuthenticationManager();
        }
    }

    private void getInternal( String repositoryResourcePath, File localFile, Resource wagonResource )
            throws TransferFailedException, SVNException, FileNotFoundException {

        fireGetInitiated( wagonResource, localFile );
        if ( !localFile.getParentFile().exists() && !localFile.getParentFile().mkdirs() ) {
            throw new TransferFailedException( "failed to create " + localFile.getParentFile() );
        }
        SVNDirEntry entry = getReadRepository().info( repositoryResourcePath, -1 );
        wagonResource.setContentLength( entry.getSize() );
        wagonResource.setLastModified( entry.getDate().getTime() );
        fireGetStarted( wagonResource, localFile );
        FileOutputStream outputStream = new FileOutputStream( localFile );
        try {
            getReadRepository().getFile( repositoryResourcePath, -1, null, outputStream );
        } catch ( SVNException e ) {
            fireTransferError( wagonResource, e, TransferEvent.REQUEST_GET );
            throw e;
        } finally {
            try {
                outputStream.close();
            } catch ( IOException ignored ) {
            }
        }
        postProcessListeners( wagonResource, localFile, TransferEvent.REQUEST_GET );
        fireGetCompleted( wagonResource, localFile );
    }

    private ISVNEditor getWriteEditor( String message ) throws SVNException {
        if ( writeEditor == null ) {
            writeAttempted = false;
            writeSuccessful = true;
            writeEditor = getWriteRepository().getCommitEditor( message, null, false, null );
            writeEditor.openRoot( -1 );
            addedEntries = new HashSet<String>();
        }
        return writeEditor;
    }

    private SVNRepository getWriteRepository() throws SVNException {
        if ( writeRepository == null ) {
            writeRepository = SVNRepositoryFactory.create( repositoryRoot );
            writeRepository.setAuthenticationManager( createAuthenticationManager() );
        }
        return writeRepository;
    }

    private void openDirectoriesInternal( ISVNEditor editor, String[] pathComponents, int offset, int count ) throws TransferFailedException, SVNException {
        String repositoryDirectoryPath = null;
        for ( int i = 0; i < count; i++ ) {
            String pathComponent = pathComponents[ offset + i ];
            repositoryDirectoryPath = repositoryDirectoryPath == null ? pathComponent : repositoryDirectoryPath + '/' + pathComponent;
            openDirectoryInternal( editor, repositoryDirectoryPath );
        }
    }

    private void openDirectoryInternal( ISVNEditor editor, String repositoryDirectoryPath ) throws TransferFailedException, SVNException {
        SVNNodeKind repositoryDirectoryKind = getReadRepository().checkPath( repositoryDirectoryPath, -1 );
        boolean repositoryDirectoryExists;
        if ( SVNNodeKind.DIR.equals( repositoryDirectoryKind ) ) {
            repositoryDirectoryExists = true;
        } else if ( SVNNodeKind.NONE.equals( repositoryDirectoryKind ) ) {
            repositoryDirectoryExists = false;
        } else {
            throw new TransferFailedException( repositoryDirectoryPath + " is not a directory" );
        }
        if ( repositoryDirectoryExists || addedEntries.contains( repositoryDirectoryPath ) ) {
            editor.openDir( repositoryDirectoryPath, -1 );
        } else {
            writeAttempted = true;
            addedEntries.add( repositoryDirectoryPath );
            editor.addDir( repositoryDirectoryPath, null, -1 );
        }
    }

    private void putFileInternal( File localFile, String repositoryResourcePath, Resource wagonResource ) throws TransferFailedException, SVNException, FileNotFoundException {
        if ( repositoryResourcePath.startsWith( "/" ) ) {
            throw new AssertionError( "unexpected repository path: " + repositoryResourcePath );
        }
        ISVNEditor editor = getWriteEditor( getCommitMessage() );
        SVNNodeKind repositoryResourceKind = getReadRepository().checkPath( repositoryResourcePath, -1 );
        boolean repositoryResourceExists;
        if ( SVNNodeKind.FILE.equals( repositoryResourceKind ) ) {
            repositoryResourceExists = true;
        } else if ( SVNNodeKind.NONE.equals( repositoryResourceKind ) ) {
            repositoryResourceExists = false;
        } else {
            throw new TransferFailedException( repositoryResourcePath + " exists and is not a file" );
        }
        firePutInitiated( wagonResource, localFile );
        wagonResource.setContentLength( localFile.length() );
        wagonResource.setLastModified( localFile.lastModified() );
        firePutStarted( wagonResource, localFile );
        SVNDeltaGenerator deltaGenerator = new SVNDeltaGenerator();
        FileInputStream inputStream = new FileInputStream( localFile );
        try {
            if ( repositoryResourceExists || addedEntries.contains( repositoryResourcePath ) ) {
                writeAttempted = true;
                editor.openFile( repositoryResourcePath, -1 );
            } else {
                writeAttempted = true;
                addedEntries.add( repositoryResourcePath );
                editor.addFile( repositoryResourcePath, null, -1 );
            }
            editor.applyTextDelta( repositoryResourcePath, null );
            String checksum = deltaGenerator.sendDelta( repositoryResourcePath, inputStream, editor, true );
            editor.closeFile( repositoryResourcePath, checksum );
        } catch ( SVNException e ) {
            fireTransferError( wagonResource, e, TransferEvent.REQUEST_PUT );
            throw e;
        } finally {
            try {
                inputStream.close();
            } catch ( IOException ignored ) {
            }
        }
        postProcessListeners( wagonResource, localFile, TransferEvent.REQUEST_PUT );
        firePutCompleted( wagonResource, localFile );
    }

    private void closeDirectoriesInternal( ISVNEditor editor, int count ) throws SVNException {
        for ( int i = 0; i < count; i++ ) {
            closeDirectoryInternal( editor );
        }
    }

    private void closeDirectoryInternal( ISVNEditor editor ) throws SVNException {
        editor.closeDir();
    }

    private String getCommitMessage() {
        return StringUtils.isNotEmpty( commitMessage )
                ? commitMessage
                : "committed by svn wagon";
    }

    private void commitWriteSession() throws SVNException {
        if ( writeEditor != null ) {
            try {
                if ( writeAttempted && writeSuccessful ) {
                    writeEditor.closeDir();
                    writeEditor.closeEdit();
                } else {
                    writeEditor.abortEdit();
                }
            } finally {
                writeEditor = null;
                addedEntries = null;
            }
        }
    }

}
