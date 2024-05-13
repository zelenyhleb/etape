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
package ru.zelenyhleb.etape.preferences.core;

import java.io.IOException;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public final class Preference {

	private final ScopedPreferenceStore store;
	private final String key;

	public Preference(ScopedPreferenceStore store, String key) {
		this.store = store;
		this.key = key;
	}

	public String get() {
		return store.getString(key);
	}

	public void set(String value) {
		store.setValue(key, value);
		try {
			store.save();
		} catch (IOException e) {
			Platform.getLog(getClass())
					.error(NLS.bind("Can not save preference value {1} to preference {0}", key, value), e); //$NON-NLS-1$
		}
	}

	public String key() {
		return key;
	}

}
