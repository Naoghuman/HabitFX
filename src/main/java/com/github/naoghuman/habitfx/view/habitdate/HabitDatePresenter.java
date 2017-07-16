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
package com.github.naoghuman.habitfx.view.habitdate;

import static com.github.naoghuman.habitfx.configuration.IActionConfiguration.ON_ACTION__REFRESH_NAVIGATION;
import com.github.naoghuman.habitfx.entities.HabitDate;
import com.github.naoghuman.habitfx.entities.HabitDateState;
import com.github.naoghuman.habitfx.sql.SqlProvider;
import com.github.naoghuman.lib.action.api.ActionFacade;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class HabitDatePresenter implements Initializable {
    
    @FXML private Button bDone;
    @FXML private Button bFailed;
    @FXML private Label lHabitDate;
    @FXML private Region rColorBar;
    @FXML private HBox hbHabitDateView;
    
    private HabitDate habitDate;
    private String dynamicActionId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize HabitDatePresenter"); // NOI18N
        
    }
    
    public void configure(final HabitDate habitDate, final String dynamicActionId) {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure HabitDate: " + habitDate.getHabitDate()); // NOI18N
        
        this.habitDate = habitDate;
        this.dynamicActionId = dynamicActionId;
        
        this.configureBackgrounds();
        this.configureInfoData();
    }
    
    private void configureBackgrounds() {
        LoggerFacade.getDefault().debug(this.getClass(), "Configure [Background]s"); // NOI18N
        
        // Compute the background color
        Color backgroundColor = Color.LEMONCHIFFON;
        if (habitDate.getState().equals(HabitDateState.DONE)) {
            backgroundColor = Color.LIGHTGREEN;
        }
        else if (habitDate.getState().equals(HabitDateState.FAILED)) {
            backgroundColor = Color.LIGHTCORAL;
        }
        // Create the new color for the hole component
        final Stop[] sComponent = new Stop[] { 
            new Stop(0, backgroundColor), 
            new Stop(1, backgroundColor.darker())};
        final LinearGradient lgComponent = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, sComponent);
        final BackgroundFill bfComponent = new BackgroundFill(lgComponent, CornerRadii.EMPTY, Insets.EMPTY);
        final Background bComponent = new Background(bfComponent);
        
        hbHabitDateView.setBackground(bComponent);
        
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
        
        lHabitDate.setText("Date: " + habitDate.getHabitDate()); // XXX property
        
        // Buttons
        boolean habitDateViewIsDisabled = Boolean.TRUE;
        if (habitDate.getState().equals(HabitDateState.NOT_STARTED)) {
            habitDateViewIsDisabled = LocalDate.now().isBefore(habitDate.getHabitDateAsLocalDate());
        }
        
        this.onActionConfigureButtons(!habitDateViewIsDisabled);
    }
    
    public void onActionHabitDateIsDone() {
        LoggerFacade.getDefault().debug(this.getClass(), String.format("On action [HabitDate=%s] is done!", // NOI18N
                habitDate.getHabitDate()));
        
        this.onActionUserClickHabitDate("-fx-background-color: AQUAMARINE;", HabitDateState.DONE); // NOI18N XXX
    }
    
    public void onActionHabitDateIsFailed() {
        LoggerFacade.getDefault().debug(this.getClass(), String.format("On action [HabitDate=%s] is failed!", // NOI18N
                habitDate.getHabitDate()));
        
        this.onActionUserClickHabitDate("-fx-background-color: FIREBRICK;", HabitDateState.FAILED); // NOI18N XXX
    }
    
    private void onActionConfigureButtons(final boolean isManagedAndVisible) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action set [Button]s managed and visible: " + isManagedAndVisible); // NOI18N
        
        bDone.setManaged(isManagedAndVisible);
        bDone.setVisible(isManagedAndVisible);
        bFailed.setManaged(isManagedAndVisible);
        bFailed.setVisible(isManagedAndVisible);
    }
    
    private void onActionUserClickHabitDate(final String style, final HabitDateState state) {
        LoggerFacade.getDefault().debug(this.getClass(), "On action user click [HabitDate]"); // NOI18N
        
        this.onActionConfigureButtons(Boolean.FALSE);
        
        hbHabitDateView.setStyle(style);
        habitDate.setState(state);
        
        SqlProvider.getDefault().updateHabitDate(habitDate);
        SqlProvider.getDefault().updateHabit(habitDate);
        
        // Refresh gui
        ActionFacade.getDefault().handle(dynamicActionId);
        ActionFacade.getDefault().handle(ON_ACTION__REFRESH_NAVIGATION);
    }
    
}
