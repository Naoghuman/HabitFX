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
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
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
@Table(name = IModelConfiguration.ENTITY__TABLE__HABITDATE)
@NamedQueries({
    @NamedQuery(
            name = IModelConfiguration.NAMED_QUERY__NAME__HABITDATE_FIND_BY_HABITID,
            query = IModelConfiguration.NAMED_QUERY__QUERY__HABITDATE_FIND_BY_HABITID),
    @NamedQuery(
            name = IModelConfiguration.NAMED_QUERY__NAME__HABITDATE_FIND_BY_HABITID_AND_DATE,
            query = IModelConfiguration.NAMED_QUERY__QUERY__HABITDATE_FIND_BY_HABITID_AND_DATE)
})
public class HabitDate implements Comparable<HabitDate>, Externalizable, IModelConfiguration {
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = DEFAULT_ID__HABITDATE;

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
    
    // START  HABIT-ID ---------------------------------------------------------
    private LongProperty habitIdProperty;
    private long _habitId = DEFAULT_ID__HABITDATE;

    @Column(name = COLUMN__HABIT__ID)
    public long getHabitId() {
        if (habitIdProperty == null) {
            return _habitId;
        } else {
            return habitIdProperty.get();
        }
    }

    public final void setHabitId(long habitId) {
        if (habitIdProperty == null) {
            _habitId = habitId;
        } else {
            habitIdProperty.set(habitId);
        }
    }

    public LongProperty habitIdProperty() {
        if (habitIdProperty == null) {
            habitIdProperty = new SimpleLongProperty(this, COLUMN__HABIT__ID, _habitId);
        }
        return habitIdProperty;
    }
    // END  HABIT-ID -----------------------------------------------------------
    
    // START  HABITDATE --------------------------------------------------------
    private StringProperty habitDateProperty;
    private String _habitDate = SIGN__EMPTY;

    @Column(name = COLUMN__HABIT__DATE)
    public String getHabitDate() {
        if (habitDateProperty == null) {
            return _habitDate;
        } else {
            return habitDateProperty.get();
        }
    }
    
    @Transient
    public LocalDate getHabitDateAsLocalDate() {
        return LocalDate.parse(this.getHabitDate(), DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public final void setHabitDate(String habitDate) {
        if (habitDateProperty == null) {
            _habitDate = habitDate;
        } else {
            habitDateProperty.set(habitDate);
        }
    }

    public final void setHabitDateAsLocalDate(LocalDate habitDate) {
        this.setHabitDate(habitDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }

    public StringProperty habitDateProperty() {
        if (habitDateProperty == null) {
            habitDateProperty = new SimpleStringProperty(this, COLUMN__HABIT__DATE, _habitDate);
        }
        return habitDateProperty;
    }
    // END  HABITDATE ----------------------------------------------------------
    
    // START  HABITDATE-STATE --------------------------------------------------
    private ObjectProperty<HabitDateState> stateProperty;
    private HabitDateState _State = HabitDateState.NOT_STARTED;

    @Column(name = COLUMN__HABIT__DATE_STATE)
    public HabitDateState getState() {
        if (stateProperty == null) {
            return _State;
        } else {
            return stateProperty.get();
        }
    }

    public final void setState(HabitDateState state) {
        if (stateProperty == null) {
            _State = state;
        } else {
            stateProperty.set(state);
        }
    }

    public ObjectProperty stateProperty() {
        if (stateProperty == null) {
            stateProperty = new SimpleObjectProperty(this, COLUMN__HABIT__DATE_STATE, _State);
        }
        return stateProperty;
    }
    // END  HABITDATE-STATE ----------------------------------------------------
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
                .append(this.getHabitId())
                .append(this.getHabitDate())
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
        
        final HabitDate other = (HabitDate) obj;
        
        return new EqualsBuilder()
                .append(this.getId(),        other.getId())
                .append(this.getHabitId(),   other.getHabitId())
                .append(this.getHabitDate(), other.getHabitDate())
                .isEquals();
    }
    
    @Override
    public int compareTo(HabitDate other) {
        return new CompareToBuilder()
                .append(this.getId(),        other.getId())
                .append(this.getHabitId(),   other.getHabitId())
                .append(this.getHabitDate(), other.getHabitDate())
                .toComparison();
    }
	
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(COLUMN__ID,              this.getId())
                .append(COLUMN__HABIT__ID,        this.getHabitId())
                .append(COLUMN__HABIT__DATE,       this.getHabitDate())
                .append(COLUMN__HABIT__DATE_STATE, this.getState())
                .toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getHabitId());
        out.writeObject(this.getHabitDate());
        out.writeObject(this.getState());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setHabitId(in.readLong());
        this.setHabitDate(String.valueOf(in.readObject()));
        this.setState((HabitDateState) in.readObject());
    }
}
