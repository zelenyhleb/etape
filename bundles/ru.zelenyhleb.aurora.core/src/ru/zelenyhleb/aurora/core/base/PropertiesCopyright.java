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
package ru.zelenyhleb.aurora.core.base;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PropertiesCopyright extends CopyrightFormat {

	@Override
	protected String format(String raw) {
		StringBuilder builder = new StringBuilder();
		builder.append("###############################################################################\n"); //$NON-NLS-1$
		builder.append(Stream.of(raw.split("\n")).map(line -> "# " + line).collect(Collectors.joining("\n"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		builder.append("\n###############################################################################\n"); //$NON-NLS-1$
		return builder.toString();
	}

	@Override
	public String extension() {
		return "properties"; //$NON-NLS-1$
	}

}
