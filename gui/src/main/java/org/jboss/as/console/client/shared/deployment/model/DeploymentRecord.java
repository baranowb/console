/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */

package org.jboss.as.console.client.shared.deployment.model;

/**
 * @author Heiko Braun
 * @date 1/31/11
 */
public interface DeploymentRecord  {

    public String getName();
    public void setName(String name);

    public String getStatus();
    public void setStatus(String status);

    public String getPath();
    public void setPath(String path);

    public DeploymentRecord getParent();
    public void setParent(DeploymentRecord parent);

    public String getRelativeTo();
    public void setRelativeTo(String relativeTo);

    public String getRuntimeName();
    public void setRuntimeName(String runtimeName);

    public String getSha();
    public void setSha(String sha);

    public String getServerGroup();
    public void setServerGroup(String groupName);

    public boolean isEnabled();
    public void setEnabled(boolean enabaled);

    public boolean isPersistent();
    public void setPersistent(boolean isPersistent);

    public boolean isArchive();
    public void setArchive(boolean isArchive);

    public boolean isSubdeployment();
    public void setSubdeployment(boolean subdeployment);

    public boolean isHasSubsystems();
    public void setHasSubsystems(boolean hasSubsystems);

    public boolean isHasSubdeployments();
    public void setHasSubdeployments(boolean hasSubdeployments);
}

