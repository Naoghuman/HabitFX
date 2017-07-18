/*
 * Copyright (C) 2017 Naoghuman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.naoghuman.habitfx.dialog;

import com.github.naoghuman.habitfx.configuration.IActionConfiguration;
import com.github.naoghuman.habitfx.dialog.habitwizard.HabitWizardPresenter;
import com.github.naoghuman.habitfx.dialog.habitwizard.HabitWizardView;
import com.github.naoghuman.habitfx.entities.Habit;
import com.github.naoghuman.habitfx.sql.SqlProvider;
import com.github.naoghuman.lib.action.core.ActionHandlerFacade;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

/**
 *
 * @author Naoghuman
 */
public final class DialogProvider implements IActionConfiguration {
    
    private static final Optional<DialogProvider> INSTANCE = Optional.of(new DialogProvider());

    public static final DialogProvider getDefault() {
        return INSTANCE.get();
    }
    
    private DialogProvider() {
        
    }
    
    public void showHabitWizard() {
        LoggerFacade.getDefault().debug(DialogProvider.class, "Show HabitWizard"); // NOI18N
        
        final Dialog dialog = new Dialog();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Habit Wizard");// TODO replace with property (use on property)
        dialog.setResizable(false);
        
        final HabitWizardView view = new HabitWizardView();
        dialog.getDialogPane().setContent(view.getView());
        
        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        
        final HabitWizardPresenter presenter = view.getRealPresenter();
        dialog.showAndWait()
                .ifPresent(response -> {
                    if (
                            response == ButtonType.OK
                            && presenter.isValid()
                    ) {
                        final Habit habit = presenter.getHabit();
                        SqlProvider.getDefault().createHabit(habit);
                        SqlProvider.getDefault().createHabitDates(habit);
                        
                        ActionHandlerFacade.getDefault().handle(ON_ACTION__REFRESH_NAVIGATION);
                    }
                });
    }
    
}
