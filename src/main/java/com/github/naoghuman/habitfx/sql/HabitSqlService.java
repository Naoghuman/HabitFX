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
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public interface HabitSqlService {

    void createHabit(final Habit habit);

    ObservableList<Habit> findAllHabits();

    Optional<Habit> findHabit(long habitId);

    void updateHabit(final HabitDate habitDate);
    
}
