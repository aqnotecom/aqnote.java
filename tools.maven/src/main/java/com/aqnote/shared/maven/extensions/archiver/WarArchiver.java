/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.maven.extensions.archiver;

import java.io.File;
import java.io.IOException;

import org.codehaus.plexus.archiver.ArchiveEntry;
import org.codehaus.plexus.archiver.ArchiverException;
import org.codehaus.plexus.archiver.jar.JarArchiver;
import org.codehaus.plexus.archiver.util.ResourceUtils;
import org.codehaus.plexus.archiver.zip.ZipOutputStream;

public class WarArchiver extends JarArchiver {

    private File    deploymentDescriptor;
    private boolean ignoreWebxml = true;
    private boolean descriptorAdded;

    public void setIgnoreWebxml(boolean ignore) {
        this.ignoreWebxml = ignore;
    }

    public WarArchiver(){
        this.archiveType = "war";
    }

    public void setWebxml(File descr) throws ArchiverException {
        this.deploymentDescriptor = descr;
        if (!(this.deploymentDescriptor.exists())) {
            throw new ArchiverException("Deployment descriptor: " + this.deploymentDescriptor + " does not exist.");
        }

        addFile(descr, "WEB-INF" + File.separatorChar + "web.xml");
    }

    public void addLib(File fileName) throws ArchiverException {
        addDirectory(fileName.getParentFile(), "WEB-INF/lib/", new String[] { fileName.getName() }, null);
    }

    public void addLibs(File directoryName, String[] includes, String[] excludes) throws ArchiverException {
        addDirectory(directoryName, "WEB-INF/lib/", includes, excludes);
    }

    public void addClass(File fileName) throws ArchiverException {
        addDirectory(fileName.getParentFile(), "WEB-INF/classes/", new String[] { fileName.getName() }, null);
    }

    public void addClasses(File directoryName, String[] includes, String[] excludes) throws ArchiverException {
        addDirectory(directoryName, "WEB-INF/classes/", includes, excludes);
    }

    public void addWebinf(File directoryName, String[] includes, String[] excludes) throws ArchiverException {
        addDirectory(directoryName, "WEB-INF/", includes, excludes);
    }

    protected void initZipOutputStream(ZipOutputStream zOut) throws IOException, ArchiverException {
        if ((this.ignoreWebxml) && (this.deploymentDescriptor == null) && (!(isInUpdateMode()))) {
            throw new ArchiverException(
                                        "webxml attribute is required (or pre-existing WEB-INF/web.xml if executing in update mode)");
        }
        super.initZipOutputStream(zOut);
    }

    protected void zipFile(ArchiveEntry entry, ZipOutputStream zOut, String vPath) throws IOException,
                                                                                  ArchiverException {
        if (vPath.equalsIgnoreCase("WEB-INF/web.xml")) {
            if ((this.descriptorAdded)
                || ((this.ignoreWebxml) && (((this.deploymentDescriptor == null) || (!(ResourceUtils.isCanonicalizedSame(entry.getResource(),
                                                                                                                         this.deploymentDescriptor))))))) {
                getLogger().warn("Warning: selected " + this.archiveType
                                         + " files include a WEB-INF/web.xml which will be ignored "
                                         + "\n(webxml attribute is missing from " + this.archiveType
                                         + " task, or ignoreWebxml attribute is specified as 'true')");
            } else {
                super.zipFile(entry, zOut, vPath);
                this.descriptorAdded = true;
            }

        } else super.zipFile(entry, zOut, vPath);
    }

    protected void cleanUp() {
        this.descriptorAdded = false;
        this.ignoreWebxml = true;
        super.cleanUp();
    }
}
