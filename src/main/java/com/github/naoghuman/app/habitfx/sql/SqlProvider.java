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
package com.github.naoghuman.app.habitfx.sql;

import com.github.naoghuman.app.habitfx.entity.EntityHabit;
import com.github.naoghuman.app.habitfx.entity.EntityHabitDate;
import com.github.naoghuman.lib.logger.core.LoggerFacade;
import java.util.Optional;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.time.StopWatch;

/**
 *
 * @author Naoghuman
 */
public class SqlProvider implements SqlServiceHabitDate, SqlServiceHabit {
    
    private static final Optional<SqlProvider> INSTANCE = Optional.of(new SqlProvider());

    public static final SqlProvider getDefault() {
        return INSTANCE.get();
    }
    
    private SqlServiceHabitDate sqlServiceHabitDate;
    private SqlServiceHabit     sqlServiceHabit;
    
    private SqlProvider() {
        this.initialize();
    }
    
    private void initialize() {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize SqlProvider"); // NOI18N
        
        sqlServiceHabit     = new DefaultSqlServiceHabit();
        sqlServiceHabitDate = new DefaultSqlServiceHabitDate();
    }
    
    @Override
    public void createHabit(final EntityHabit habit) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        sqlServiceHabit.createHabit(habit);
        
        this.print(stopWatch, 1, "createHabit(Habit)"); // NOI18N
    }

    @Override
    public int createHabitDates(final EntityHabit habit) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final int countEntities = sqlServiceHabitDate.createHabitDates(habit);
        
        this.print(stopWatch, countEntities, "createHabitDates(Habit)"); // NOI18N
    
        return countEntities;
    }
    
    @Override
    public ObservableList<EntityHabitDate> findAllHabitDates(final EntityHabit habit) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<EntityHabitDate> habitDates = sqlServiceHabitDate.findAllHabitDates(habit);
        
        this.print(stopWatch, habitDates.size(), "findAllHabitDates(Habit)"); // NOI18N
        
        return habitDates;
    }

    @Override
    public ObservableList<EntityHabit> findAllHabits() {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final ObservableList<EntityHabit> habits = sqlServiceHabit.findAllHabits();
        
        this.print(stopWatch, habits.size(), "findAllHabits()"); // NOI18N
        
        return habits;
    }

    @Override
    public Optional<EntityHabit> findHabit(final long habitId) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final Optional<EntityHabit> optional = sqlServiceHabit.findHabit(habitId);
        
        this.print(stopWatch, 1, "findHabit(long)"); // NOI18N
        
        return optional;
    }
    
    @Override
    public Optional<EntityHabitDate> findHabitDate(final long habitId, final String habitDate) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        final Optional<EntityHabitDate> optional = sqlServiceHabitDate.findHabitDate(habitId, habitDate);
        
        this.print(stopWatch, 1, "findHabitDate(long, String)"); // NOI18N
        
        return optional;
    }

    @Override
    public void updateHabit(final EntityHabitDate habitDate) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        sqlServiceHabit.updateHabit(habitDate);
        
        this.print(stopWatch, 1, "updateHabit(HabitDate)"); // NOI18N
    }

    @Override
    public void updateHabitDate(final EntityHabitDate habitDate) {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        sqlServiceHabitDate.updateHabitDate(habitDate);
        
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
