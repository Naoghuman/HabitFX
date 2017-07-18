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
package com.github.naoghuman.habitfx.application;

import com.github.naoghuman.habitfx.configuration.IActionConfiguration;
import com.github.naoghuman.habitfx.dialog.DialogProvider;
import com.github.naoghuman.habitfx.entities.Habit;
import com.github.naoghuman.habitfx.sql.SqlProvider;
import com.github.naoghuman.habitfx.view.habitnavigation.HabitNavigationPresenter;
import com.github.naoghuman.habitfx.view.habitnavigation.HabitNavigationView;
import com.github.naoghuman.habitfx.view.habitoverview.HabitOverviewPresenter;
import com.github.naoghuman.habitfx.view.habitoverview.HabitOverviewView;
import com.github.naoghuman.lib.action.core.ActionHandlerFacade;
import com.github.naoghuman.lib.action.core.RegisterActions;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

/**
 *
 * @author Naoghuman
 */
public class ApplicationPresenter implements Initializable, IActionConfiguration, RegisterActions {
    
    @FXML private BorderPane bpHabit;
    @FXML private ListView<Habit> lvHabits;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ApplicationPresenter"); // NOI18N
        
//        assert (apView != null) : "fx:id=\"apView\" was not injected: check your FXML file 'Application.fxml'."; // NOI18N
        
        this.initializeListView();

        this.register();
        
        ActionHandlerFacade.getDefault().handle(ON_ACTION__REFRESH_NAVIGATION);
    }
    
    public void initializeAfterWindowIsShowing() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ApplicationPresenter after window is showing"); // NOI18N
    }

    private void initializeListView() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize ListView"); // NOI18N
        
        final Callback callbackHabits = (Callback<ListView<Habit>, ListCell<Habit>>) (ListView<Habit> listView) -> new ListCell<Habit>() {
            @Override
            protected void updateItem(final Habit habit, final boolean empty) {
                super.updateItem(habit, empty);
                
                this.setText(null);
                this.setGraphic(null);
                
                if (habit != null && !empty) {
                    final HabitNavigationView view = new HabitNavigationView();
                    final HabitNavigationPresenter presenter = view.getRealPresenter();
                    presenter.configure(habit);
                    
                    this.setGraphic(view.getView());
                }
            }
        };
        lvHabits.setCellFactory(callbackHabits);
        lvHabits.setOnMouseClicked(event -> {
            // Open the Term
            if (
                    event.getClickCount() == 2
                    && !lvHabits.getSelectionModel().isEmpty()
            ) {
                // Open the Habit
                final Habit habit = lvHabits.getSelectionModel().getSelectedItem();
                this.onActionShowHabitInOverview(habit);
            }
        });
        lvHabits.setOnKeyPressed(event -> {
            final KeyCode keyCode = event.getCode();
            if (
                    keyCode.equals(KeyCode.ENTER)
                    || keyCode.equals(KeyCode.SPACE)
                    || keyCode.equals(KeyCode.TAB)
            ) {
                // Open the Habit
                final Habit habit = lvHabits.getSelectionModel().getSelectedItem();
                this.onActionShowHabitInOverview(habit);
            }
        });
    }
    
    public void onActionAddHabit() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action add [Habit]"); // NOI18N
        
        DialogProvider.getDefault().showHabitWizard();
    }
    
    public void onActionEditHabit() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action edit [Habit]"); // NOI18N
        
    }
    
    public void onActionRefreshNavigation() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action refresh [Navigation]"); // NOI18N
        
        lvHabits.getItems().clear();
        
        final ObservableList<Habit> habits = SqlProvider.getDefault().findAllHabits();
        lvHabits.getItems().addAll(habits);
    }

    private void onActionShowHabitInOverview(Habit habit) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action show [Habit] in [Overview]"); // NOI18N

        final HabitOverviewView view = new HabitOverviewView();
        final HabitOverviewPresenter presenter = view.getRealPresenter();
        presenter.configure(habit);
        
        bpHabit.setCenter(view.getView());
    }
    
    @Override
    public void register() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register in ApplicationPresenter"); // NOI18N
        
        this.registerOnActionRefreshNavigation();
    }

    private void registerOnActionRefreshNavigation() {
        LoggerFacade.getDefault().debug(this.getClass(), "Register on action refresh [Navigation]"); // NOI18N
        
        ActionHandlerFacade.getDefault().register(
                ON_ACTION__REFRESH_NAVIGATION,
                (ActionEvent event) -> {
                    this.onActionRefreshNavigation();
                });
    }
    
}
