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
import java.util.Optional;
import javafx.collections.ObservableList;

/**
 *
 * @author Naoghuman
 */
public interface SqlServiceHabit {

    void createHabit(final EntityHabit habit);

    ObservableList<EntityHabit> findAllHabits();

    Optional<EntityHabit> findHabit(long habitId);

    void updateHabit(final EntityHabitDate habitDate);
    
}
