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
package ru.zelenyhleb.etape.preferences.ui;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import ru.zelenyhleb.etape.preferences.Messages;
import ru.zelenyhleb.etape.preferences.PreferencesId;
import ru.zelenyhleb.etape.preferences.core.Preferences;

public final class CopyrightPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(InstanceScope.INSTANCE, new PreferencesId().get()));
		setDescription(Messages.CopyrightPreferencePage_label_description);
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(new Preferences().copyright().key(),
				Messages.CopyrightPreferencePage_label_copyright, StringFieldEditor.UNLIMITED, 10,
				StringFieldEditor.VALIDATE_ON_KEY_STROKE, getFieldEditorParent()));
	}

}
