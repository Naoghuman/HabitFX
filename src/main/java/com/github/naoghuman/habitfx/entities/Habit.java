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
package com.github.naoghuman.habitfx.entities;

import com.github.naoghuman.habitfx.configuration.IModelConfiguration;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author Naoghuman
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = IModelConfiguration.ENTITY__TABLE__HABIT)
@NamedQueries({
    @NamedQuery(
            name  = IModelConfiguration.NAMED_QUERY__NAME__HABIT_FIND_ALL,
            query = IModelConfiguration.NAMED_QUERY__QUERY__HABIT_FIND_ALL),
    @NamedQuery(
            name  = IModelConfiguration.NAMED_QUERY__NAME__HABIT_FIND_BY_ID,
            query = IModelConfiguration.NAMED_QUERY__QUERY__HABIT_FIND_BY_ID)
})
public class Habit implements Comparable<Habit>, Externalizable, IModelConfiguration {
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID__HABIT;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = COLUMN__ID)
    public long getId() {
        if (idProperty == null) {
            return _id;
        } else {
            return idProperty.get();
        }
    }

    public final void setId(long id) {
        if (idProperty == null) {
            _id = id;
        } else {
            idProperty.set(id);
        }
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, COLUMN__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = COLUMN__GENERATION_TIME)
    public long getGenerationTime() {
        if (generationTimeProperty == null) {
            return _generationTime;
        } else {
            return generationTimeProperty.get();
        }
    }

    public final void setGenerationTime(long generationTime) {
        if (generationTimeProperty == null) {
            _generationTime = generationTime;
        } else {
            generationTimeProperty.set(generationTime);
        }
    }

    public LongProperty generationTimeProperty() {
        if (generationTimeProperty == null) {
            generationTimeProperty = new SimpleLongProperty(this, COLUMN__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  TITLE ------------------------------------------------------------
    private StringProperty titleProperty;
    private String _title = SIGN__EMPTY;

    @Column(name = COLUMN__TITLE)
    public String getTitle() {
        if (titleProperty == null) {
            return _title;
        } else {
            return titleProperty.get();
        }
    }

    public final void setTitle(String title) {
        if (titleProperty == null) {
            _title = title;
        } else {
            titleProperty.set(title);
        }
    }

    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new SimpleStringProperty(this, COLUMN__TITLE, _title);
        }
        return titleProperty;
    }
    // END  TITLE --------------------------------------------------------------
    
    // START  START-DATE -------------------------------------------------------
    private StringProperty startDateProperty;
    private String _startDate = SIGN__EMPTY;

    @Column(name = COLUMN__HABIT__START_DATE)
    public String getStartDate() {
        if (startDateProperty == null) {
            return _startDate;
        } else {
            return startDateProperty.get();
        }
    }
    
    @Transient
    public LocalDate getStartDateAsLocalDate() {
        return LocalDate.parse(this.getStartDate(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public final void setStartDate(String startDate) {
        if (startDateProperty == null) {
            _startDate = startDate;
        } else {
            startDateProperty.set(_startDate);
        }
    }

    public final void setStartDateAsLocalDate(LocalDate startDate) {
        this.setStartDate(startDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public StringProperty startDateProperty() {
        if (startDateProperty == null) {
            startDateProperty = new SimpleStringProperty(this, COLUMN__HABIT__START_DATE, _startDate);
        }
        return startDateProperty;
    }
    // END  START-DATE ---------------------------------------------------------
    
    // START  END-DATE ---------------------------------------------------------
    private StringProperty endDateProperty;
    private String _endDate = SIGN__EMPTY;

    @Column(name = COLUMN__HABIT__END_DATE)
    public String getEndDate() {
        if (endDateProperty == null) {
            return _endDate;
        } else {
            return endDateProperty.get();
        }
    }
    
    @Transient
    public LocalDate getEndDateAsLocalDate() {
        return LocalDate.parse(this.getEndDate(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public final void setEndDate(String endDate) {
        if (endDateProperty == null) {
            _endDate = endDate;
        } else {
            endDateProperty.set(_endDate);
        }
    }

    public final void setEndDateAsLocalDate(LocalDate endDate) {
        this.setEndDate(endDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public StringProperty endDateProperty() {
        if (endDateProperty == null) {
            endDateProperty = new SimpleStringProperty(this, COLUMN__HABIT__END_DATE, _endDate);
        }
        return endDateProperty;
    }
    // END  END-DATE -----------------------------------------------------------
    
    // START  HABIT-STATE ------------------------------------------------------
    private ObjectProperty<HabitState> stateProperty;
    private HabitState _state = HabitState.NOT_STARTED;

    @Column(name = COLUMN__HABIT__STATE)
    public HabitState getState() {
        if (stateProperty == null) {
            return _state;
        } else {
            return stateProperty.get();
        }
    }

    public final void setState(HabitState state) {
        if (stateProperty == null) {
            _state = state;
        } else {
            stateProperty.set(state);
        }
    }

    public ObjectProperty stateProperty() {
        if (stateProperty == null) {
            stateProperty = new SimpleObjectProperty(this, COLUMN__HABIT__STATE, _state);
        }
        return stateProperty;
    }
    // END  HABIT-STATE --------------------------------------------------------
    
    // START HABIT-PERIOD ------------------------------------------------------
    private IntegerProperty periodProperty;
    private int _period = 0;

    @Column(name = COLUMN__HABIT__PERIOD)
    public int getPeriod() {
        if (periodProperty == null) {
            return _period;
        } else {
            return periodProperty.get();
        }
    }

    public final void setPeriod(int period) {
        if (periodProperty == null) {
            _period = period;
        } else {
            periodProperty.set(_period);
        }
    }

    public IntegerProperty periodProperty() {
        if (periodProperty == null) {
            periodProperty = new SimpleIntegerProperty(this, COLUMN__HABIT__PERIOD, _period);
        }
        return periodProperty;
    }
    // END HABIT-PERIOD --------------------------------------------------------
    
    // START HABIT-COUNTER-DONE ------------------------------------------------
    private IntegerProperty counterDoneProperty;
    private int _counterDone = 0;

    @Column(name = COLUMN__HABIT__COUNTER_DONE)
    public int getCounterDone() {
        if (counterDoneProperty == null) {
            return _counterDone;
        } else {
            return counterDoneProperty.get();
        }
    }

    public final void setCounterDone(int counterDone) {
        if (counterDoneProperty == null) {
            _counterDone = counterDone;
        } else {
            counterDoneProperty.set(_counterDone);
        }
    }

    public IntegerProperty counterDoneProperty() {
        if (counterDoneProperty == null) {
            counterDoneProperty = new SimpleIntegerProperty(this, COLUMN__HABIT__COUNTER_DONE, _counterDone);
        }
        return counterDoneProperty;
    }
    // END HABIT-COUNTER-DONE --------------------------------------------------
    
    // START HABIT-COUNTER-FAILED ----------------------------------------------
    private IntegerProperty counterFailedProperty;
    private int _counterFailed = 0;

    @Column(name = COLUMN__HABIT__COUNTER_FAILED)
    public int getCounterFailed() {
        if (counterFailedProperty == null) {
            return _counterFailed;
        } else {
            return counterFailedProperty.get();
        }
    }

    public final void setCounterFailed(int counterFailed) {
        if (counterFailedProperty == null) {
            _counterFailed = counterFailed;
        } else {
            counterFailedProperty.set(_counterFailed);
        }
    }

    public IntegerProperty counterFailedProperty() {
        if (counterFailedProperty == null) {
            counterFailedProperty = new SimpleIntegerProperty(this, COLUMN__HABIT__COUNTER_FAILED, _counterFailed);
        }
        return counterFailedProperty;
    }
    // END HABIT-COUNTER-FAILED ------------------------------------------------
    
    // START HABIT-COUNTER-NOT-STARTED -----------------------------------------
    private IntegerProperty counterNotStartedProperty;
    private int _counterNotStarted = 0;

    @Column(name = COLUMN__HABIT__COUNTER_NOT_STARTED)
    public int getCounterNotStarted() {
        if (counterNotStartedProperty == null) {
            return _counterNotStarted;
        } else {
            return counterNotStartedProperty.get();
        }
    }

    public final void setCounterNotStarted(int counterNotStarted) {
        if (counterNotStartedProperty == null) {
            _counterNotStarted = counterNotStarted;
        } else {
            counterNotStartedProperty.set(_counterNotStarted);
        }
    }

    public IntegerProperty counterNotStartedProperty() {
        if (counterNotStartedProperty == null) {
            counterNotStartedProperty = new SimpleIntegerProperty(this, COLUMN__HABIT__COUNTER_NOT_STARTED, _counterNotStarted);
        }
        return counterNotStartedProperty;
    }
    // END HABIT-COUNTER-NOT-STARTED -------------------------------------------

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getTitle())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Habit other = (Habit) obj;
        
        return new EqualsBuilder()
                .append(this.getId(),    other.getId())
                .append(this.getTitle(), other.getTitle())
                .isEquals();
    }
    
    @Override
    public int compareTo(Habit other) {
        return new CompareToBuilder()
                .append(this.getTitle(), other.getTitle())
                .append(this.getId(),    other.getId())
                .toComparison();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(COLUMN__ID,                this.getId())
                .append(COLUMN__GENERATION_TIME,   this.getGenerationTime())
                .append(COLUMN__TITLE,             this.getTitle())
                .append(COLUMN__HABIT__START_DATE, this.getStartDate())
                .append(COLUMN__HABIT__END_DATE,   this.getEndDate())
                .append(COLUMN__HABIT__STATE,      this.getState())
                .append(COLUMN__HABIT__PERIOD,     this.getPeriod())
                .append(COLUMN__HABIT__COUNTER_DONE,        this.getCounterDone())
                .append(COLUMN__HABIT__COUNTER_FAILED,      this.getCounterFailed())
                .append(COLUMN__HABIT__COUNTER_NOT_STARTED, this.getCounterNotStarted())
                .toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getTitle());
        out.writeObject(this.getStartDate());
        out.writeObject(this.getEndDate());
        out.writeObject(this.getState());
        out.writeInt(this.getPeriod());
        out.writeInt(this.getCounterDone());
        out.writeInt(this.getCounterFailed());
        out.writeInt(this.getCounterNotStarted());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setTitle(String.valueOf(in.readObject()));
        this.setStartDate(String.valueOf(in.readObject()));
        this.setEndDate(String.valueOf(in.readObject()));
        this.setState((HabitState) in.readObject());
        this.setPeriod(in.readInt());
        this.setCounterDone(in.readInt());
        this.setCounterFailed(in.readInt());
        this.setCounterNotStarted(in.readInt());
    }
    
}
