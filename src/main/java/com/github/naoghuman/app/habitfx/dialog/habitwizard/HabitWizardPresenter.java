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
package com.github.naoghuman.app.habitfx.dialog.habitwizard;

import com.github.naoghuman.app.habitfx.entity.EntityHabit;
import com.github.naoghuman.app.habitfx.entity.EntityHabitState;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import com.github.naoghuman.app.habitfx.configuration.ConfigurationAction;
import com.github.naoghuman.app.habitfx.configuration.ConfigurationProperties;

/**
 *
 * @author Naoghuman
 */
public class HabitWizardPresenter implements Initializable, ConfigurationAction, ConfigurationProperties {
    
    @FXML private DatePicker dpStartDate;
    @FXML private Label lInfoEndDate;
    @FXML private Label lWarningCantEmpty;
    @FXML private TextField tfHabitTitle;

    private ResourceBundle resources = null;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize HabitWizardPresenter"); // NOI18N
        
        this.resources = resources;
        
        this.initializeDatePicker();
        this.initializeTextField();
    }

    private void initializeDatePicker() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize DatePicker"); // NOI18N
        
        dpStartDate.setEditable(false);
        dpStartDate.setValue(LocalDate.now());
        dpStartDate.valueProperty()
                .addListener((ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) -> {
                    final String infoEndDate = resources.getString(HABITWIZARD_LABEL_INFO_ENDDATE);
                    final String endDate = newValue.plusWeeks(3).minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
                    lInfoEndDate.setText(infoEndDate.replace("%s", endDate)); // NOI18N
                });
        
        final String infoEndDate = resources.getString(HABITWIZARD_LABEL_INFO_ENDDATE);
        final String endDate = dpStartDate.getValue().plusWeeks(3).minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
        lInfoEndDate.setText(infoEndDate.replace("%s", endDate)); // NOI18N
    }

    private void initializeTextField() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize TextField"); // NOI18N
        
        lWarningCantEmpty.setManaged(true);
        lWarningCantEmpty.setVisible(true);
        
        tfHabitTitle.textProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    // Show warning
                    if (
                            !oldValue.trim().isEmpty()
                            && newValue.trim().isEmpty()
                            && !lWarningCantEmpty.isManaged()
                            && !lWarningCantEmpty.isVisible()
                    ) {
                        lWarningCantEmpty.setManaged(true);
                        lWarningCantEmpty.setVisible(true);
                    }

                    // Hide warning
                    if (
                            oldValue.trim().isEmpty()
                            && !newValue.trim().isEmpty()
                            && lWarningCantEmpty.isManaged()
                            && lWarningCantEmpty.isVisible()
                    ) {
                        lWarningCantEmpty.setManaged(false);
                        lWarningCantEmpty.setVisible(false);
                    }
                });
    }
    
    public EntityHabit getHabit() {
        LoggerFacade.getDefault().debug(this.getClass(), "Get [Habit]"); // NOI18N
        
        final EntityHabit habit = new EntityHabit();
        habit.setTitle(tfHabitTitle.getText().trim());
        habit.setCounterDone(0);
        habit.setCounterFailed(0);
        habit.setStartDateAsLocalDate(dpStartDate.getValue());
        habit.setEndDateAsLocalDate(habit.getStartDateAsLocalDate().plusWeeks(3).minusDays(1));
        
        EntityHabitState habitState = EntityHabitState.ACTIVE;
        if (habit.getStartDateAsLocalDate().isAfter(LocalDate.now())) {
            habitState = EntityHabitState.NOT_STARTED;
        }
        habit.setState(habitState);
        
        final LocalDate startDate = habit.getStartDateAsLocalDate();
        final LocalDate endDate   = habit.getEndDateAsLocalDate();
        final Period period       = Period.between(startDate, endDate.plusDays(1));
        habit.setCounterNotStarted(period.getDays());
        habit.setPeriod(period.getDays());
        
        return habit;
    }

    public boolean isValid() {
        return !tfHabitTitle.getText().trim().isEmpty();
    }
    
}
