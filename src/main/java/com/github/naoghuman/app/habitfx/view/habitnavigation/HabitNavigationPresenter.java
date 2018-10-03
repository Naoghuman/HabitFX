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
package com.github.naoghuman.app.habitfx.view.habitnavigation;

import com.github.naoghuman.app.habitfx.entities.Habit;
import com.github.naoghuman.app.habitfx.entities.HabitDate;
import com.github.naoghuman.app.habitfx.entities.HabitState;
import com.github.naoghuman.app.habitfx.sql.SqlProvider;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

/**
 *
 * @author Naoghuman
 */
public class HabitNavigationPresenter implements Initializable {
    
    @FXML private HBox hbNavigationElement;
    @FXML private Label lCurrentDateState;
    @FXML private Label lCurrentHabitCounters;
    @FXML private Label lEndDate;
    @FXML private Label lHabitState;
    @FXML private Label lStartDate;
    @FXML private Label lTitle;
    @FXML private Region rColorBar;
    
    private Habit habit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize HabitDatePresenter"); // NOI18N
        
    }
    
    public void configure(Habit habit) {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure"); // NOI18N
        
        this.habit = habit;
        
        this.configureBackgrounds();
        this.configureInfoData();
    }
    
    private void configureBackgrounds() {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure [Background]s"); // NOI18N
        
        // Compute the background color
        Color backgroundColor = Color.LEMONCHIFFON;
        if (habit.getState().equals(HabitState.ACTIVE)) {
            backgroundColor = Color.LIGHTGREEN;
        }
        else if (habit.getState().equals(HabitState.FINISHED)) {
            backgroundColor = Color.LIGHTCORAL;
        }
        // Create the new color for the hole component
//        final Stop[] sComponent = new Stop[] { 
//            new Stop(0, backgroundColor), 
//            new Stop(1, backgroundColor.darker())};
//        final LinearGradient lgComponent = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, sComponent);
//        final BackgroundFill bfComponent = new BackgroundFill(lgComponent, CornerRadii.EMPTY, Insets.EMPTY);
//        final Background bComponent = new Background(bfComponent);
        
//        hbNavigationElement.setBackground(bComponent);
        
        // Create the new color for the color bar
        final Stop[] sColorBar = new Stop[] { 
            new Stop(0, backgroundColor.brighter()), 
            new Stop(1, backgroundColor)};
        final LinearGradient lgColorBar = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, sColorBar);
        final BackgroundFill bfColorBar = new BackgroundFill(lgColorBar, CornerRadii.EMPTY, Insets.EMPTY);
        final Background bColorBar = new Background(bfColorBar);
        
        rColorBar.setBackground(bColorBar);
    }
    
    private void configureInfoData() {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure [Info] data"); // NOI18N
        
        lTitle.setText(habit.getTitle());
        lTitle.setTooltip(new Tooltip("title"));// NOI18N
        
        lCurrentHabitCounters.setText(String.format("(%s, %s, %s)", habit.getCounterDone(), habit.getCounterNotStarted(), habit.getCounterFailed()));
        lCurrentHabitCounters.setTooltip(new Tooltip("(done, not started, failed)"));// NOI18N
        
        lStartDate.setText(String.format("%s", habit.getStartDate())); // NOI18N
        lStartDate.setTooltip(new Tooltip("startdate"));// NOI18N
        
        lEndDate.setText(String.format("%s", habit.getEndDate())); // NOI18N
        lEndDate.setTooltip(new Tooltip("enddate"));// NOI18N
        
        lHabitState.setText(habit.getState().name());
        lHabitState.setTooltip(new Tooltip("habit-state"));// NOI18N
        
        final String habitDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        final Optional<HabitDate> result = SqlProvider.getDefault().findHabitDate(habit.getId(), habitDate);
        lCurrentDateState.setText(result.isPresent() ? result.get().getState().name() : "---");
        lCurrentDateState.setTooltip(result.isPresent() ? new Tooltip("current habitdate-state") : null);// NOI18N
    }
    
}
