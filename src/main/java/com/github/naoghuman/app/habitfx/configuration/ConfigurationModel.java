/*
 * Copyright (C) 2017 PRo
 Naoghuman
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
package com.github.naoghuman.app.habitfx.configuration;

/**
 *
 * @author Naoghuman
 */
public interface ConfigurationModel {
    
    public static final String COLUMN__GENERATION_TIME = "generationTime"; // NOI18N
    public static final String COLUMN__ID              = "id"; // NOI18N
    public static final String COLUMN__TITLE           = "title"; // NOI18N
    
    public static final String COLUMN__HABIT__COUNTER_DONE        = "habitCounterDone"; // NOI18N
    public static final String COLUMN__HABIT__COUNTER_FAILED      = "habitCounterFailed"; // NOI18N
    public static final String COLUMN__HABIT__COUNTER_NOT_STARTED = "habitCounterNotStarted"; // NOI18N
    public static final String COLUMN__HABIT__END_DATE   = "habitEndDate"; // NOI18N
    public static final String COLUMN__HABIT__DATE       = "habitDate"; // NOI18N
    public static final String COLUMN__HABIT__DATE_STATE = "habitDateState"; // NOI18N
    public static final String COLUMN__HABIT__ID         = "habitId"; // NOI18N
    public static final String COLUMN__HABIT__PERIOD     = "habitPeriod"; // NOI18N
    public static final String COLUMN__HABIT__START_DATE = "habitStartDate"; // NOI18N
    public static final String COLUMN__HABIT__STATE      = "habitState"; // NOI18N
    
    public static final Long DEFAULT_ID__HABIT     = 1_000_000_000_000L;
    public static final Long DEFAULT_ID__HABITDATE = 1_000_000_000_100L;
    
    public static final String ENTITY__TABLE__HABIT     = "EntityHabit"; // NOI18N
    public static final String ENTITY__TABLE__HABITDATE = "EntityHabitDate"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__HABIT_FIND_ALL            = "EntityHabit.findAll"; // NOI18N
    public static final String NAMED_QUERY__NAME__HABIT_FIND_BY_ID          = "EntityHabit.findById"; // NOI18N
    public static final String NAMED_QUERY__NAME__HABITDATE_FIND_BY_HABITID = "EntityHabitDate.findByHabitId"; // NOI18N
    public static final String NAMED_QUERY__NAME__HABITDATE_FIND_BY_HABITID_AND_DATE = "EntityHabitDate.findByHabitIdAndDate"; // NOI18N
    
    public static final String NAMED_QUERY__QUERY__HABIT_FIND_ALL            = "SELECT eh FROM EntityHabit eh"; // NOI18N
    public static final String NAMED_QUERY__QUERY__HABIT_FIND_BY_ID          = "SELECT eh FROM EntityHabit eh  WHERE eh.id = :id"; // NOI18N
    public static final String NAMED_QUERY__QUERY__HABITDATE_FIND_BY_HABITID = "SELECT ehd FROM EntityHabitDate ehd  WHERE ehd.habitId = :habitId"; // NOI18N
    public static final String NAMED_QUERY__QUERY__HABITDATE_FIND_BY_HABITID_AND_DATE  = "SELECT ehd FROM EntityHabitDate ehd  WHERE ehd.habitId = :habitId AND ehd.habitDate = :habitDate"; // NOI18N
    
    public static final String SIGN__EMPTY = ""; // NOI18N
    
}
