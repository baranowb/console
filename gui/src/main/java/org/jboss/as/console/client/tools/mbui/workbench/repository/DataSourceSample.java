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
package org.jboss.as.console.client.tools.mbui.workbench.repository;

import org.jboss.as.console.client.mbui.aui.aim.Builder;
import org.jboss.as.console.client.mbui.aui.aim.Container;
import org.jboss.as.console.client.mbui.aui.aim.InteractionUnit;
import org.jboss.as.console.client.mbui.aui.aim.Select;
import org.jboss.as.console.client.mbui.aui.aim.as7.Form;
import org.jboss.as.console.client.mbui.aui.mapping.Mapping;
import org.jboss.as.console.client.mbui.aui.mapping.as7.ResourceAttribute;
import org.jboss.as.console.client.mbui.aui.mapping.as7.ResourceMapping;

import static org.jboss.as.console.client.mbui.aui.aim.TemporalOperator.Choice;
import static org.jboss.as.console.client.mbui.aui.aim.TemporalOperator.OrderIndependance;

/**
 * @author Harald Pehl
 * @date 10/25/2012
 */
public class DataSourceSample implements Sample
{
    @Override
    public String getName()
    {
        return "Datasource";
    }

    @Override
    public InteractionUnit build()
    {
        String namespace = "org.jboss.ds";

        // mappings
        ResourceMapping global = new ResourceMapping(namespace)
                .setAddress("/profile={0}/subsystem=datasources/data-source={1}");

        Mapping tableMapping = new ResourceMapping(namespace)
                .addAttribute(new ResourceAttribute("${resource.name}", "Name"))
                .addAttributes("jndi-name", "enabled");

        Mapping basicAttributesMapping = new ResourceMapping(namespace)
                .addAttribute(new ResourceAttribute("${resource.name}", "Name"))
                .addAttributes("jndi-name", "enabled", "driver-name", "share-prepared-statements",
                        "prepared-statements-cache-size");

        Mapping connectionAttributesMapping = new ResourceMapping(namespace)
                .addAttributes("connection-url", "new-connection-sql", "jta", "use-ccm");

        // UI
        InteractionUnit root = new Builder()
            .start(new Container(namespace, "datasources", "Datasources", Choice))
            .addMapping(global)
                .start(new Container(namespace, "regularDS", "Regular", OrderIndependance))
                    .add(new Select(namespace, "datasourceTable", "DatasourceList"))
                    .addMapping(tableMapping)
                    .start(new Container(namespace, "datasourceAttributes", "Datasource", Choice))
                        .add(new Form(namespace, "basicAttributes", "Attributes"))
                        .addMapping(basicAttributesMapping)
                        .add(new Form(namespace, "connectionAttributes", "Connection"))
                        .addMapping(connectionAttributesMapping)
                    .end()
                .end()
                .start(new Container(namespace, "xsDS", "XA", OrderIndependance))
                .end()
            .end().build();

        return root;
    }
}