/*******************************************************************************
 * Copyright (c) 2022 Nikifor Fedorov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 *
 * Contributors:
 *     Nikifor Fedorov - initial API and implementation
 *******************************************************************************/
package ru.zelenyhleb.etape.ui;

import org.eclipse.osgi.util.NLS;

public final class Messages extends NLS {

	private static final String BUNDLE_NAME = "ru.zelenyhleb.etape.ui.messages"; //$NON-NLS-1$
	public static String BaseBundleTemplate_label;
	public static String BaseBundleTemplate_description;
	public static String ConfigurationPage_groupLabel;
	public static String ConfigurationPage_displayNameLabel;
	public static String ConfigurationPage_versionLabel;
	public static String ConfigurationPage_vendorLabel;
	public static String ConfigurationPage_copyrightLabel;
	public static String ConfigurationPage_label_generate;
	public static String ConfigurationPage_label_changePreference;
	public static String ConfigurationPage_e_emptyName;
	public static String ConfigurationPage_e_emptyVendor;
	public static String ConfigurationPage_w_emptyVersion;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
