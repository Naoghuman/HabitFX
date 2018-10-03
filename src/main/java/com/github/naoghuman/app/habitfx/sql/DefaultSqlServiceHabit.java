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
import com.github.naoghuman.app.habitfx.entity.EntityHabitDateState;
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.github.naoghuman.app.habitfx.configuration.ConfigurationModel;

/**
 *
 * @author Naoghuman
 */
public final class DefaultSqlServiceHabit implements ConfigurationModel, SqlServiceHabit {

    @Override
    public void createHabit(final EntityHabit habit) {
        if (Objects.equals(habit.getId(), DEFAULT_ID__HABIT)) {
            habit.setId(System.nanoTime());
            DatabaseFacade.getDefault().getCrudService().create(habit);
        }
        else {
            this.update(habit);
        }
    }

    @Override
    public ObservableList<EntityHabit> findAllHabits() {
        final ObservableList<EntityHabit> allHabits = FXCollections.observableArrayList();
        final List<EntityHabit> habits = DatabaseFacade.getDefault()
                .getCrudService()
                .findByNamedQuery(EntityHabit.class, NAMED_QUERY__NAME__HABIT_FIND_ALL);
        
        allHabits.addAll(habits);
        Collections.sort(allHabits);

        return allHabits;
    }

    @Override
    public Optional<EntityHabit> findHabit(final long habitId) {
        final EntityHabit habit = DatabaseFacade.getDefault()
                .getCrudService()
                .findById(EntityHabit.class, habitId);
        
        return (habit != null) ? Optional.ofNullable(habit) : Optional.empty();
    }
    
    private void update(final EntityHabit habit) {
        DatabaseFacade.getDefault().getCrudService().update(habit);
    }

    @Override
    public void updateHabit(final EntityHabitDate habitDate) {
        final EntityHabit habit = DatabaseFacade.getDefault().getCrudService().findById(EntityHabit.class, habitDate.getHabitId());
        habit.setCounterNotStarted(habit.getCounterNotStarted() - 1);
        
        if (habitDate.getState().equals(EntityHabitDateState.DONE)) {
            habit.setCounterDone(habit.getCounterDone() + 1);
        }
        
        if (habitDate.getState().equals(EntityHabitDateState.FAILED)) {
            habit.setCounterFailed(habit.getCounterFailed() + 1);
        }
        
        this.update(habit);
    }
    
}
