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

import com.github.naoghuman.habitfx.configuration.IModelConfiguration;
import com.github.naoghuman.habitfx.entities.Habit;
import com.github.naoghuman.habitfx.entities.HabitDate;
import com.github.naoghuman.habitfx.entities.HabitDateState;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public final class DefaultHabitSqlService implements IModelConfiguration, HabitSqlService {

    @Override
    public void createHabit(final Habit habit) {
        if (Objects.equals(habit.getId(), DEFAULT_ID__HABIT)) {
            habit.setId(System.nanoTime());
            DatabaseFacade.getDefault().getCrudService().create(habit);
        }
        else {
            this.update(habit);
        }
    }

    @Override
    public ObservableList<Habit> findAllHabits() {
        final ObservableList<Habit> allHabits = FXCollections.observableArrayList();
        final List<Habit> habits = DatabaseFacade.getDefault()
                .getCrudService()
                .findByNamedQuery(Habit.class, NAMED_QUERY__NAME__HABIT_FIND_ALL);
        
        allHabits.addAll(habits);
        Collections.sort(allHabits);

        return allHabits;
    }

    @Override
    public Optional<Habit> findHabit(final long habitId) {
        final Habit habit = DatabaseFacade.getDefault()
                .getCrudService()
                .findById(Habit.class, habitId);
        
        return (habit != null) ? Optional.ofNullable(habit) : Optional.empty();
    }
    
    private void update(final Habit habit) {
        DatabaseFacade.getDefault().getCrudService().update(habit);
    }

    @Override
    public void updateHabit(final HabitDate habitDate) {
        final Habit habit = DatabaseFacade.getDefault().getCrudService().findById(Habit.class, habitDate.getHabitId());
        habit.setCounterNotStarted(habit.getCounterNotStarted() - 1);
        
        if (habitDate.getState().equals(HabitDateState.DONE)) {
            habit.setCounterDone(habit.getCounterDone() + 1);
        }
        
        if (habitDate.getState().equals(HabitDateState.FAILED)) {
            habit.setCounterFailed(habit.getCounterFailed() + 1);
        }
        
        this.update(habit);
    }
    
}
