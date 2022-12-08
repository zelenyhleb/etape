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
package ru.zelenyhleb.etape.ui.wizards.bundle;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.pde.ui.IFieldData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import ru.zelenyhleb.etape.core.heuristic.QualifiedName;
import ru.zelenyhleb.etape.preferences.core.Preferences;
import ru.zelenyhleb.etape.ui.Messages;

public final class ConfigurationPage extends WizardPage implements IPageChangedListener {

	private Text displayName;
	private Text version;
	private Text vendor;
	private Text copyright;
	private Link changePreference;
	private final ModifyListener validate = e -> validate();
	private final ModifyListener changeVisibility = e -> changeVisibility();
	private final Supplier<IFieldData> data;

	public ConfigurationPage(Supplier<IFieldData> data) {
		super("Create project"); //$NON-NLS-1$
		this.data = data;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayoutFactory.swtDefaults().applyTo(container);
		Group configuration = new Group(container, SWT.NONE);
		configuration.setText(Messages.ConfigurationPage_groupLabel);
		GridLayoutFactory.swtDefaults().numColumns(3).equalWidth(true).applyTo(configuration);
		GridDataFactory grab = GridDataFactory.swtDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false);
		Label displayNameLabel = new Label(configuration, SWT.NONE);
		displayNameLabel.setText(Messages.ConfigurationPage_displayNameLabel);
		displayName = new Text(configuration, SWT.BORDER);
		displayName.addModifyListener(validate);
		grab.span(2, 1).applyTo(displayName);
		Label versionLabel = new Label(configuration, SWT.NONE);
		versionLabel.setText(Messages.ConfigurationPage_versionLabel);
		version = new Text(configuration, SWT.BORDER);
		version.addModifyListener(validate);
		grab.span(2, 1).applyTo(version);
		Label vendorLabel = new Label(configuration, SWT.NONE);
		vendorLabel.setText(Messages.ConfigurationPage_vendorLabel);
		vendor = new Text(configuration, SWT.BORDER);
		vendor.addModifyListener(validate);
		grab.span(2, 1).applyTo(vendor);
		Label copyrightLabel = new Label(configuration, SWT.NONE);
		copyrightLabel.setText(Messages.ConfigurationPage_copyrightLabel);
		copyright = new Text(configuration, SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		copyright.addModifyListener(changeVisibility);
		Button generate = new Button(configuration, SWT.PUSH);
		generate.setText(Messages.ConfigurationPage_label_generate);
		generate.addListener(SWT.Selection, e -> generate());
		changePreference = new Link(configuration, SWT.NONE);
		changePreference.setText(Messages.ConfigurationPage_label_changePreference);
		changePreference.addListener(SWT.Selection, e -> updatePreference());
		changePreference.setVisible(false);
		changePreference.setEnabled(false);
		grab.span(2, 1).applyTo(changePreference);
		grab.grab(true, true).align(SWT.FILL, SWT.FILL).applyTo(configuration);
		grab.applyTo(copyright);
		setControl(container);
		validate();
		Optional.of(getContainer()) //
				.filter(IPageChangeProvider.class::isInstance) //
				.map(IPageChangeProvider.class::cast) //
				.ifPresent(p -> p.addPageChangedListener(this));
	}

	public String displayName() {
		return extractText(displayName, data.get().getName());
	}

	public String version() {
		return extractText(version, data.get().getVersion());
	}

	public String vendor() {
		return extractText(vendor, data.get().getProvider());
	}

	public String copyright() {
		return extractText(copyright, storedCopyright());
	}

	private String storedCopyright() {
		return new Preferences().copyright().get();
	}

	private String extractText(Text text, String other) {
		return Optional.ofNullable(text).map(Text::getText).filter(s -> !s.isEmpty()).orElse(other);
	}

	private void generate() {
		QualifiedName heuristic = new QualifiedName(data.get().getId());
		displayName.setText(heuristic.name());
		vendor.setText(heuristic.vendor());
		version.setText(heuristic.version());
		copyright.setText(storedCopyright());
	}

	private void changeVisibility() {
		if (!copyright.getText().equals(new Preferences().copyright().get())) {
			changePreference.setVisible(true);
			changePreference.setEnabled(true);
		} else {
			changePreference.setVisible(false);
			changePreference.setEnabled(false);
		}
	}

	private void updatePreference() {
		new Preferences().copyright().set(copyright.getText());
		changeVisibility();
	}

	private void validate() {
		setErrorMessage(null);
		if (displayName.getText().isEmpty() || displayName.getText().isBlank()) {
			setErrorMessage(Messages.ConfigurationPage_e_emptyName);
			setPageComplete(false);
			return;
		}
		if (vendor.getText().isEmpty() || vendor.getText().isBlank()) {
			setErrorMessage(Messages.ConfigurationPage_e_emptyVendor);
			setPageComplete(false);
			return;
		}
		if (version.getText().isEmpty() || version.getText().isBlank()) {
			setMessage(Messages.ConfigurationPage_w_emptyVersion, WARNING);
		}
		setPageComplete(true);
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		if (event.getSelectedPage().getClass().equals(this.getClass())) {
			displayName.setText(displayName());
			version.setText(version());
			vendor.setText(vendor());
			copyright.setText(storedCopyright());
		}
	}

}
