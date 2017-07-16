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
package com.github.naoghuman.habitfx.sql;

import com.github.naoghuman.habitfx.entities.Habit;
import com.github.naoghuman.habitfx.entities.HabitDate;
import com.github.naoghuman.lib.logger.api.LoggerFacade;
import java.util.Optional;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Naoghuman
 */
public class SqlProvider implements HabitDateSqlService, HabitSqlService {
    
    private static final Optional<SqlProvider> INSTANCE = Optional.of(new SqlProvider());

    public static final SqlProvider getDefault() {
        return INSTANCE.get();
    }
    
    private HabitDateSqlService habitDateSqlService;
    private HabitSqlService habitSqlService;
    
    private SqlProvider() {
        this.initialize();
    }
    
    private void initialize() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize SqlProvider"); // NOI18N
        
        habitSqlService = new DefaultHabitSqlService();
        habitDateSqlService = new DefaultHabitDateSqlService();
    }
    
    @Override
    public void createHabit(final Habit habit) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        habitSqlService.createHabit(habit);
        
        this.print(stopWatch, 1, "createHabit(Habit)"); // NOI18N
    }

    @Override
    public int createHabitDates(final Habit habit) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final int countEntities = habitDateSqlService.createHabitDates(habit);
        
        this.print(stopWatch, countEntities, "createHabitDates(Habit)"); // NOI18N
    
        return countEntities;
    }
    
    @Override
    public ObservableList<HabitDate> findAllHabitDates(final Habit habit) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<HabitDate> habitDates = habitDateSqlService.findAllHabitDates(habit);
        
        this.print(stopWatch, habitDates.size(), "findAllHabitDates(Habit)"); // NOI18N
        
        return habitDates;
    }

    @Override
    public ObservableList<Habit> findAllHabits() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<Habit> habits = habitSqlService.findAllHabits();
        
        this.print(stopWatch, habits.size(), "findAllHabits()"); // NOI18N
        
        return habits;
    }

    @Override
    public Optional<Habit> findHabit(final long habitId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final Optional<Habit> optional = habitSqlService.findHabit(habitId);
        
        this.print(stopWatch, 1, "findHabit(long)"); // NOI18N
        
        return optional;
    }
    
    @Override
    public Optional<HabitDate> findHabitDate(final long habitId, final String habitDate) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final Optional<HabitDate> optional = habitDateSqlService.findHabitDate(habitId, habitDate);
        
        this.print(stopWatch, 1, "findHabitDate(long, String)"); // NOI18N
        
        return optional;
    }

    @Override
    public void updateHabit(final HabitDate habitDate) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        habitSqlService.updateHabit(habitDate);
        
        this.print(stopWatch, 1, "updateHabit(HabitDate)"); // NOI18N
    }

    @Override
    public void updateHabitDate(final HabitDate habitDate) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        habitDateSqlService.updateHabitDate(habitDate);
        
        this.print(stopWatch, 1, "updateHabitDate(HabitDate)"); // NOI18N
    }
    
    private void print(final StopWatch stopWatch, final int entities, final String method) {
        stopWatch.split();
        
        final StringBuilder sb = new StringBuilder();
        sb.append("  + Need "); // NOI18N
        sb.append(stopWatch.toSplitString());
        sb.append(" for [");
        sb.append(entities);
        sb.append("] entities in [");
        sb.append(method);
        sb.append("]");
        
        LoggerFacade.getDefault().debug(this.getClass(), sb.toString());
        
        stopWatch.stop();
    }
    
}
