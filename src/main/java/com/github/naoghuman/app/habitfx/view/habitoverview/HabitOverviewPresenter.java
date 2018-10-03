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
package com.github.naoghuman.app.habitfx.view.habitoverview;

import com.github.naoghuman.app.habitfx.configuration.IActionConfiguration;
import com.github.naoghuman.app.habitfx.entities.Habit;
import com.github.naoghuman.app.habitfx.entities.HabitDate;
import com.github.naoghuman.app.habitfx.entities.HabitDateState;
import com.github.naoghuman.app.habitfx.sql.SqlProvider;
import com.github.naoghuman.app.habitfx.view.habitdate.HabitDatePresenter;
import com.github.naoghuman.app.habitfx.view.habitdate.HabitDateView;
import com.github.naoghuman.lib.action.core.ActionHandlerFacade;
import com.github.naoghuman.lib.action.core.RegisterActions;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Naoghuman
 */
public class HabitOverviewPresenter implements Initializable, RegisterActions {

    @FXML private Label lCounterDone;
    @FXML private Label lCounterFailed;
    @FXML private Label lCounterNotStarted;
    
    @FXML private Label lHabitTitle;
    @FXML private VBox vbDoneHabits;
    @FXML private VBox vbFailedHabits;
    @FXML private VBox vbNotStartedHabits;
    
    private String dynamicActionId = IActionConfiguration.ON_ACTION__LOAD_HABITDATES;
    
    private Habit habit;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize HabitPresenter"); // NOI18N
        
    }
    
    public void configure(Habit habit) {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure"); // NOI18N
        
        this.habit = habit;
        
        dynamicActionId = dynamicActionId + habit.getId();
        
        this.register();
        
        final boolean refreshHabit = Boolean.FALSE;
        this.onActionRefreshHabitDates(refreshHabit);
    }
    
    private void configureHabitDate(VBox vbox, HabitDate habitDate) {
        final HabitDateView view = new HabitDateView();
        final HabitDatePresenter presenter = view.getRealPresenter();
        presenter.configure(habitDate, dynamicActionId);

        vbox.getChildren().add(view.getView());
    }
    
    private void onActionRefreshHabitDates(final boolean refreshHabit) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action load [HabitDate]s"); // NOI18N
        
        if (refreshHabit) {
            final Optional<Habit> optional = SqlProvider.getDefault().findHabit(habit.getId());
            if (optional.isPresent()) {
                this.habit = optional.get();
            }
        }
        
        // Show Habit title
        lHabitTitle.setText("Title: " + habit.getTitle()); // NOI18N
        
        // Show info in Counters
        lCounterDone      .setText(String.format("Done: %d",        habit.getCounterDone()));       // NOI18N
        lCounterFailed    .setText(String.format("Failed: %d",      habit.getCounterFailed()));     // NOI18N
        lCounterNotStarted.setText(String.format("Not started: %d", habit.getCounterNotStarted())); // NOI18N
        
        // Show DONE elements
        final ObservableList<HabitDate> habitDates = SqlProvider.getDefault().findAllHabitDates(habit);
        vbDoneHabits.getChildren().clear();
        habitDates.stream()
                .filter(habitDate -> 
                        habitDate.getState().equals(HabitDateState.DONE)
                )
                .forEach(habitDate -> {
                    this.configureHabitDate(vbDoneHabits, habitDate);
                });
        
        // Show NOT_STARTED elements
        vbNotStartedHabits.getChildren().clear();
        habitDates.stream()
                .filter(habitDate -> 
                        habitDate.getState().equals(HabitDateState.NOT_STARTED)
                )
                .forEach(habitDate -> {
                    this.configureHabitDate(vbNotStartedHabits, habitDate);
                });
        
        // Show FAILED elements
        vbFailedHabits.getChildren().clear();
        habitDates.stream()
                .filter(habitDate -> 
                        habitDate.getState().equals(HabitDateState.FAILED)
                )
                .forEach(habitDate -> {
                    this.configureHabitDate(vbFailedHabits, habitDate);
                });
    }

    @Override
    public void register() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register"); // NOI18N
        
        this.registerOnActionRefreshHabitDates();
    }

    private void registerOnActionRefreshHabitDates() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action refresh [HabitDate]s"); // NOI18N
        
        // TODO should be removed if the view is removed from BorderPane
        ActionHandlerFacade.getDefault()
                .register(
                        dynamicActionId,
                        (ActionEvent event) -> {
                            final boolean refreshHabit = Boolean.TRUE;
                            this.onActionRefreshHabitDates(refreshHabit);
                        });
    }
    
}
