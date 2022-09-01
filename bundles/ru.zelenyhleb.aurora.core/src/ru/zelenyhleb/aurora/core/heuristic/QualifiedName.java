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
package ru.zelenyhleb.aurora.core.heuristic;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.zelenyhleb.aurora.core.Messages;

public final class QualifiedName {

	private static final String SPACE = " "; //$NON-NLS-1$
	private final String[] segments;

	public QualifiedName(String qualified) {
		this.segments = qualified.split("\\."); //$NON-NLS-1$
	}

	public String qualified() {
		return Stream.of(segments).collect(Collectors.joining(".")); //$NON-NLS-1$
	}

	public String name() {
		if (segments.length <= 1)
			return Messages.QualifiedName_defaultBundleName;
		else if (segments.length < 3)
			return Stream.of(segments).map(this::capitalize).collect(Collectors.joining(SPACE));
		else
			return Stream.of(Arrays.copyOfRange(segments, 2, segments.length)) //
					.map(this::capitalize) //
					.collect(Collectors.joining(SPACE));
	}

	public String vendor() {
		if (segments.length >= 2) {
			return capitalize(segments[1]) //
					.concat(SPACE) //
					.concat(new VendorSuffix().apply(segments[0]));
		} else {
			return Messages.QualifiedName_defaultBundleVendor;
		}
	}

	public String version() {
		return Messages.QualifiedName_defaultBundleVersion;
	}

	private String capitalize(String raw) {
		String first = String.valueOf(raw.charAt(0));
		return raw.replaceFirst(first, first.toUpperCase());
	}

}
