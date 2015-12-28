/*
 * (C) Copyright 2015 Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Nuxeo
 */

package org.nuxeo.timeoff;

import static org.nuxeo.ecm.core.io.registry.reflect.Instantiations.SINGLETON;
import static org.nuxeo.ecm.core.io.registry.reflect.Priorities.REFERENCE;
import static org.nuxeo.ecm.user.center.profile.UserProfileConstants.USER_PROFILE_AVATAR_FIELD;
import static org.nuxeo.ecm.user.center.profile.UserProfileConstants.USER_PROFILE_BIRTHDATE_FIELD;
import static org.nuxeo.ecm.user.center.profile.UserProfileConstants.USER_PROFILE_PHONENUMBER_FIELD;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerator;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.core.io.download.DownloadService;
import org.nuxeo.ecm.core.io.marshallers.json.enrichers.AbstractJsonEnricher;
import org.nuxeo.ecm.core.io.registry.context.RenderingContext;
import org.nuxeo.ecm.core.io.registry.reflect.Setup;
import org.nuxeo.ecm.user.center.profile.UserProfileService;
import org.nuxeo.runtime.api.Framework;

/**
 * @author <a href="mailto:ak@nuxeo.com">Arnaud Kervern</a>
 * @since 8.1
 */
@Setup(mode = SINGLETON, priority = REFERENCE)
public class ProfileEnricher extends AbstractJsonEnricher<NuxeoPrincipal> {

    public static final String NAME = "userprofile";

    private static final Log log = LogFactory.getLog(ProfileEnricher.class);

    public ProfileEnricher() {
        super(NAME);
    }

    @Override
    public void write(JsonGenerator jg, NuxeoPrincipal nuxeoPrincipal) throws IOException {

        UserProfileService ups = Framework.getLocalService(UserProfileService.class);
        try (RenderingContext.SessionWrapper sw = ctx.getSession(null)) {
            DocumentModel up = ups.getUserProfileDocument(nuxeoPrincipal.getName(), sw.getSession());

            jg.writeFieldName(NAME);
            if (up == null) {
                jg.writeNull();
            } else {
                jg.writeStartObject();

                jg.writeStringField("birthdate", (String) up.getPropertyValue(USER_PROFILE_BIRTHDATE_FIELD));
                jg.writeStringField("phonenumber", (String) up.getPropertyValue(USER_PROFILE_PHONENUMBER_FIELD));

                Blob avatar = (Blob) up.getPropertyValue(USER_PROFILE_AVATAR_FIELD);
                if (avatar != null) {
                    DownloadService downloadService = Framework.getService(DownloadService.class);
                    String url = downloadService.getDownloadUrl(up, USER_PROFILE_AVATAR_FIELD, avatar.getFilename());
                    jg.writeStringField("avatar", ctx.getBaseUrl() + url);
                } else {
                    jg.writeNullField("avatar");
                }

                jg.writeEndObject();
            }
        }
    }
}
