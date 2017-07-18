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
import com.github.naoghuman.lib.database.core.DatabaseFacade;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public final class DefaultHabitDateSqlService implements IModelConfiguration, HabitDateSqlService {

    @Override
    public int createHabitDates(final Habit habit) {
        final LocalDate startDate = habit.getStartDateAsLocalDate();
        final LocalDate endDate   = habit.getEndDateAsLocalDate();
        final Period period       = Period.between(startDate, endDate.plusDays(1));
        
        LocalDate dateToSave = startDate;
        for (int i = 0; i < period.getDays(); i++) {
            final HabitDate habitDate = new HabitDate();
            habitDate.setId(System.nanoTime());
            habitDate.setHabitId(habit.getId());
            habitDate.setHabitDateAsLocalDate(dateToSave);
            
            DatabaseFacade.getDefault().getCrudService().create(habitDate);
            
            dateToSave = dateToSave.plusDays(1);
        }
        
        return period.getDays();
    }

    @Override
    public ObservableList<HabitDate> findAllHabitDates(final Habit habit) {
        final ObservableList<HabitDate> allHabitDates = FXCollections.observableArrayList();
        
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(COLUMN__HABIT__ID, habit.getId());
        
        final List<HabitDate> habitDates = DatabaseFacade.getDefault()
                .getCrudService()
                .findByNamedQuery(HabitDate.class,
                        NAMED_QUERY__NAME__HABITDATE_FIND_BY_HABITID,
                        parameters);
        
        allHabitDates.addAll(habitDates);
        Collections.sort(allHabitDates);

        return allHabitDates;
    }

    @Override
    public Optional<HabitDate> findHabitDate(final long habitId, final String habitDate) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put(COLUMN__HABIT__ID,   habitId);
        parameters.put(COLUMN__HABIT__DATE, habitDate);
        
        final List<HabitDate> habitDates = DatabaseFacade.getDefault()
                .getCrudService()
                .findByNamedQuery(HabitDate.class,
                        NAMED_QUERY__NAME__HABITDATE_FIND_BY_HABITID_AND_DATE,
                        parameters);
        
        return (habitDates.size() == 1) ? Optional.ofNullable(habitDates.get(0)) : Optional.empty();
    }

    @Override
    public void updateHabitDate(final HabitDate habitDate) {
        DatabaseFacade.getDefault().getCrudService().update(habitDate);
    }
    
}
