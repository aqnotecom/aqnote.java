/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.maven.extensions.archiver;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.codehaus.plexus.PlexusConstants;
import org.codehaus.plexus.PlexusContainer;
import org.codehaus.plexus.archiver.ArchivedFileSet;
import org.codehaus.plexus.archiver.Archiver;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.FileSet;
import org.codehaus.plexus.archiver.ResourceIterator;
import org.codehaus.plexus.archiver.tar.TarArchiver;
import org.codehaus.plexus.archiver.tar.TarLongFileMode;
import org.codehaus.plexus.components.io.resources.PlexusIoResource;
import org.codehaus.plexus.components.io.resources.PlexusIoResourceCollection;
import org.codehaus.plexus.context.Context;
import org.codehaus.plexus.context.ContextException;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Contextualizable;

/**
 * @author <a href="mailto:madding.lip@gmail.co">madding.lip</a>
 */
@SuppressWarnings("deprecation")
public class TgzArchiver implements Archiver, Contextualizable {

    private  PlexusContainer container;

    private volatile Archiver archiver;

    protected void initialize() throws ArchiverException {
        if (archiver == null) {
            try {
                archiver = (Archiver)container.lookup(Archiver.ROLE, "tar");
                if (archiver instanceof TarArchiver) {
                    TarArchiver tarArchiver = (TarArchiver) archiver;
                    TarArchiver.TarCompressionMethod compression = new TarArchiver.TarCompressionMethod();
                    compression.setValue("gzip");
                    tarArchiver.setCompression(compression);

                    String property = System.getProperty("assembly.tarLongFileMode");
                    if (isNotEmpty(property)) {
                        TarLongFileMode longFileMode = new TarLongFileMode();
                        longFileMode.setValue(property);
                        tarArchiver.setLongfile(longFileMode);
                    }
                }
            } catch (Exception e) {
                throw new ArchiverException(e.getMessage(), e);
            }
        }

        if (archiver == null) {
            throw new ArchiverException("Create tar archiver failed");
        }
    }

    @Override
    public void createArchive() throws ArchiverException, IOException {
        initialize();
        archiver.createArchive();
    }

    @Override
    public void addDirectory(File directory) throws ArchiverException {
        initialize();
        archiver.addDirectory(directory);
    }

    @Override
    public void addDirectory(File directory, String prefix) throws ArchiverException {
        initialize();
        archiver.addDirectory(directory, prefix);
    }

    @Override
    public void addDirectory(File directory, String[] includes, String[] excludes) throws ArchiverException {
        initialize();
        archiver.addDirectory(directory, includes, excludes);
    }

    @Override
    public void addDirectory(File directory, String prefix, String[] includes, String[] excludes) throws ArchiverException {
        initialize();
        archiver.addDirectory(directory, prefix, includes, excludes);
    }

    @Override
    public void addFileSet(FileSet fileSet) throws ArchiverException {
        initialize();
        archiver.addFileSet(fileSet);
    }

    @Override
    public void addFile(File inputFile, String destFileName) throws ArchiverException {
        initialize();
        archiver.addFile(inputFile, destFileName);
    }

    @Override
    public void addFile(File inputFile, String destFileName, int permissions) throws ArchiverException {
        initialize();
        archiver.addFile(inputFile, destFileName, permissions);
    }

    @Override
    public void addArchivedFileSet(File archiveFile) throws ArchiverException {
        initialize();
        archiver.addArchivedFileSet(archiveFile);
    }

    @Override
    public void addArchivedFileSet(File archiveFile, String prefix) throws ArchiverException {
        initialize();
        archiver.addArchivedFileSet(archiveFile, prefix);
    }

    @Override
    public void addArchivedFileSet(File archiveFile, String[] includes, String[] excludes) throws ArchiverException {
        initialize();
        archiver.addArchivedFileSet(archiveFile, includes, excludes);
    }

    @Override
    public void addArchivedFileSet(File archiveFile, String prefix, String[] includes, String[] excludes) throws ArchiverException {
        initialize();
        archiver.addArchivedFileSet(archiveFile, prefix, includes, excludes);
    }

    @Override
    public void addArchivedFileSet(ArchivedFileSet fileSet) throws ArchiverException {
        initialize();
        archiver.addArchivedFileSet(fileSet);
    }

    @Override
    public void addResource(PlexusIoResource resource, String destFileName, int permissions) throws ArchiverException {
        initialize();
        archiver.addResource(resource, destFileName, permissions);
    }

    @Override
    public void addResources(PlexusIoResourceCollection resources) throws ArchiverException {
        initialize();
        archiver.addResources(resources);
    }

    @Override
    public File getDestFile() {
        initialize();
        return archiver.getDestFile();
    }

    @Override
    public void setDestFile(File destFile) {
        initialize();
        archiver.setDestFile(destFile);
    }

    @Override
    public void setFileMode(int mode) {
        initialize();
        archiver.setFileMode(mode);
    }

    @Override
    public int getFileMode() {
        initialize();
        return archiver.getFileMode();
    }

    @Override
    public int getOverrideFileMode() {
        initialize();
        return archiver.getOverrideFileMode();
    }

    @Override
    public void setDefaultFileMode(int mode) {
        initialize();
        archiver.setDefaultFileMode(mode);
    }

    @Override
    public int getDefaultFileMode() {
        initialize();
        return archiver.getDefaultFileMode();
    }

    @Override
    public void setDirectoryMode(int mode) {
        initialize();
        archiver.setDirectoryMode(mode);
    }

    @Override
    public int getDirectoryMode() {
        initialize();
        return archiver.getDirectoryMode();
    }

    @Override
    public int getOverrideDirectoryMode() {
        initialize();
        return archiver.getOverrideDirectoryMode();
    }

    @Override
    public void setDefaultDirectoryMode(int mode) {
        initialize();
        archiver.setDefaultDirectoryMode(mode);
    }

    @Override
    public int getDefaultDirectoryMode() {
        initialize();
        return archiver.getDefaultDirectoryMode();
    }

    @Override
    public boolean getIncludeEmptyDirs() {
        initialize();
        return archiver.getIncludeEmptyDirs();
    }

    @Override
    public void setIncludeEmptyDirs(boolean includeEmptyDirs) {
        initialize();
        archiver.setIncludeEmptyDirs(includeEmptyDirs);
    }

    @Override
    public void setDotFileDirectory(File dotFileDirectory) {
        initialize();
        archiver.setDotFileDirectory(dotFileDirectory);
    }

    @Override
    public ResourceIterator getResources() throws ArchiverException {
        initialize();
        return archiver.getResources();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Map getFiles() {
        initialize();
        return archiver.getFiles();
    }

    @Override
    public boolean isForced() {
        initialize();
        return archiver.isForced();
    }

    @Override
    public void setForced(boolean forced) {
        initialize();
        archiver.setForced(forced);
    }

    @Override
    public boolean isSupportingForced() {
        initialize();
        return archiver.isSupportingForced();
    }

    @Override
    public String getDuplicateBehavior() {
        initialize();
        return archiver.getDuplicateBehavior();
    }

    @Override
    public void setDuplicateBehavior(String duplicate) {
        initialize();
        archiver.setDuplicateBehavior(duplicate);
    }

    @Override
    public void setUseJvmChmod(boolean useJvmChmod) {
        initialize();
        archiver.setUseJvmChmod(useJvmChmod);
    }

    @Override
    public boolean isUseJvmChmod() {
        initialize();
        return archiver.isUseJvmChmod();
    }

    @Override
    public boolean isIgnorePermissions() {
        initialize();
        return archiver.isIgnorePermissions();
    }

    @Override
    public void setIgnorePermissions(boolean ignorePermissions) {
        initialize();
        archiver.setIgnorePermissions(ignorePermissions);
    }

    @Override
    public void contextualize(Context context) throws ContextException {
        container = (PlexusContainer) context.get(PlexusConstants.PLEXUS_KEY);
    }

    private boolean isNotEmpty(String string) {
        return string != null && !"".equals(string.trim());
    }
}
