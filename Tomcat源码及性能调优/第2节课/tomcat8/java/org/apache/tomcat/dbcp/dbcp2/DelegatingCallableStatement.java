/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.tomcat.dbcp.dbcp2;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 * A base delegating implementation of {@link CallableStatement}.
 * <p>
 * All of the methods from the {@link CallableStatement} interface
 * simply call the corresponding method on the "delegate"
 * provided in my constructor.
 * <p>
 * Extends AbandonedTrace to implement Statement tracking and
 * logging of code which created the Statement. Tracking the
 * Statement ensures that the Connection which created it can
 * close any open Statement's on Connection close.
 *
 * @author Glenn L. Nielsen
 * @author James House
 * @author Dirk Verbeeck
 * @since 2.0
 */
public class DelegatingCallableStatement extends DelegatingPreparedStatement
        implements CallableStatement {

    /**
     * Create a wrapper for the Statement which traces this
     * Statement to the Connection which created it and the
     * code which created it.
     *
     * @param c the {@link DelegatingConnection} that created this statement
     * @param s the {@link CallableStatement} to delegate all calls to
     */
    public DelegatingCallableStatement(final DelegatingConnection<?> c,
                                       final CallableStatement s) {
        super(c, s);
    }

    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).registerOutParameter( parameterIndex,  sqlType); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).registerOutParameter( parameterIndex,  sqlType,  scale); } catch (final SQLException e) { handleException(e); } }

    @Override
    public boolean wasNull() throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).wasNull(); } catch (final SQLException e) { handleException(e); return false; } }

    @Override
    public String getString(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getString( parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public boolean getBoolean(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBoolean( parameterIndex); } catch (final SQLException e) { handleException(e); return false; } }

    @Override
    public byte getByte(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getByte( parameterIndex); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public short getShort(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getShort( parameterIndex); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public int getInt(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getInt( parameterIndex); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public long getLong(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getLong( parameterIndex); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public float getFloat(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getFloat( parameterIndex); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public double getDouble(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getDouble( parameterIndex); } catch (final SQLException e) { handleException(e); return 0; } }

    /** @deprecated */
    @Override
    @Deprecated
    public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBigDecimal( parameterIndex,  scale); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public byte[] getBytes(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBytes( parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Date getDate(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getDate( parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Time getTime(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTime( parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Timestamp getTimestamp(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTimestamp( parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Object getObject(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getObject( parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBigDecimal( parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Object getObject(final int i, final Map<String,Class<?>> map) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getObject( i, map); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Ref getRef(final int i) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getRef( i); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Blob getBlob(final int i) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBlob( i); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Clob getClob(final int i) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getClob( i); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Array getArray(final int i) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getArray( i); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getDate( parameterIndex,  cal); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTime( parameterIndex,  cal); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTimestamp( parameterIndex,  cal); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public void registerOutParameter(final int paramIndex, final int sqlType, final String typeName) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).registerOutParameter( paramIndex,  sqlType,  typeName); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void registerOutParameter(final String parameterName, final int sqlType) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).registerOutParameter(parameterName, sqlType); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void registerOutParameter(final String parameterName, final int sqlType, final int scale) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).registerOutParameter(parameterName, sqlType, scale); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void registerOutParameter(final String parameterName, final int sqlType, final String typeName) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).registerOutParameter(parameterName, sqlType, typeName); } catch (final SQLException e) { handleException(e); } }

    @Override
    public URL getURL(final int parameterIndex) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getURL(parameterIndex); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public void setURL(final String parameterName, final URL val) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setURL(parameterName, val); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setNull(final String parameterName, final int sqlType) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setNull(parameterName, sqlType); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setBoolean(final String parameterName, final boolean x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setBoolean(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setByte(final String parameterName, final byte x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setByte(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setShort(final String parameterName, final short x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setShort(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setInt(final String parameterName, final int x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setInt(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setLong(final String parameterName, final long x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setLong(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setFloat(final String parameterName, final float x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setFloat(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setDouble(final String parameterName, final double x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setDouble(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setBigDecimal(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setString(final String parameterName, final String x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setString(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setBytes(final String parameterName, final byte [] x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setBytes(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setDate(final String parameterName, final Date x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setDate(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setTime(final String parameterName, final Time x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setTime(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setTimestamp(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setAsciiStream(parameterName, x, length); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setBinaryStream(parameterName, x, length); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setObject(final String parameterName, final Object x, final int targetSqlType, final int scale) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setObject(parameterName, x, targetSqlType, scale); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setObject(parameterName, x, targetSqlType); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setObject(final String parameterName, final Object x) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setObject(parameterName, x); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setCharacterStream(final String parameterName, final Reader reader, final int length) throws SQLException
    { checkOpen(); ((CallableStatement)getDelegate()).setCharacterStream(parameterName, reader, length); }

    @Override
    public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setDate(parameterName, x, cal); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setTime(parameterName, x, cal); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setTimestamp(parameterName, x, cal); } catch (final SQLException e) { handleException(e); } }

    @Override
    public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException
    { checkOpen(); try { ((CallableStatement)getDelegate()).setNull(parameterName, sqlType, typeName); } catch (final SQLException e) { handleException(e); } }

    @Override
    public String getString(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getString(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public boolean getBoolean(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBoolean(parameterName); } catch (final SQLException e) { handleException(e); return false; } }

    @Override
    public byte getByte(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getByte(parameterName); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public short getShort(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getShort(parameterName); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public int getInt(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getInt(parameterName); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public long getLong(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getLong(parameterName); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public float getFloat(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getFloat(parameterName); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public double getDouble(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getDouble(parameterName); } catch (final SQLException e) { handleException(e); return 0; } }

    @Override
    public byte[] getBytes(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBytes(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Date getDate(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getDate(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Time getTime(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTime(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Timestamp getTimestamp(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTimestamp(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Object getObject(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getObject(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public BigDecimal getBigDecimal(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBigDecimal(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Object getObject(final String parameterName, final Map<String,Class<?>> map) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getObject(parameterName, map); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Ref getRef(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getRef(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Blob getBlob(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getBlob(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Clob getClob(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getClob(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Array getArray(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getArray(parameterName); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Date getDate(final String parameterName, final Calendar cal) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getDate(parameterName, cal); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Time getTime(final String parameterName, final Calendar cal) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTime(parameterName, cal); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getTimestamp(parameterName, cal); } catch (final SQLException e) { handleException(e); return null; } }

    @Override
    public URL getURL(final String parameterName) throws SQLException
    { checkOpen(); try { return ((CallableStatement)getDelegate()).getURL(parameterName); } catch (final SQLException e) { handleException(e); return null; } }


    @Override
    public RowId getRowId(final int parameterIndex) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getRowId(parameterIndex);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public RowId getRowId(final String parameterName) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getRowId(parameterName);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public void setRowId(final String parameterName, final RowId value) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setRowId(parameterName, value);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setNString(final String parameterName, final String value) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setNString(parameterName, value);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setNCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setNCharacterStream(parameterName, reader, length);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setNClob(final String parameterName, final NClob value) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setNClob(parameterName, value);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setClob(parameterName, reader, length);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setBlob(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setBlob(parameterName, inputStream, length);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setNClob(parameterName, reader, length);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public NClob getNClob(final int parameterIndex) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getNClob(parameterIndex);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public NClob getNClob(final String parameterName) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getNClob(parameterName);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public void setSQLXML(final String parameterName, final SQLXML value) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setSQLXML(parameterName, value);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getSQLXML(parameterIndex);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public SQLXML getSQLXML(final String parameterName) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getSQLXML(parameterName);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public String getNString(final int parameterIndex) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getNString(parameterIndex);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public String getNString(final String parameterName) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getNString(parameterName);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getNCharacterStream(parameterIndex);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public Reader getNCharacterStream(final String parameterName) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getNCharacterStream(parameterName);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public Reader getCharacterStream(final int parameterIndex) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getCharacterStream(parameterIndex);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public Reader getCharacterStream(final String parameterName) throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getCharacterStream(parameterName);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public void setBlob(final String parameterName, final Blob blob) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setBlob(parameterName, blob);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setClob(final String parameterName, final Clob clob) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setClob(parameterName, clob);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setAsciiStream(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setAsciiStream(parameterName, inputStream, length);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setBinaryStream(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setBinaryStream(parameterName, inputStream, length);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setCharacterStream(parameterName, reader, length);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setAsciiStream(final String parameterName, final InputStream inputStream) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setAsciiStream(parameterName, inputStream);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setBinaryStream(final String parameterName, final InputStream inputStream) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setBinaryStream(parameterName, inputStream);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setCharacterStream(parameterName, reader);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setNCharacterStream(final String parameterName, final Reader reader) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setNCharacterStream(parameterName, reader);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setClob(final String parameterName, final Reader reader) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setClob(parameterName, reader);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setBlob(parameterName, inputStream);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public void setNClob(final String parameterName, final Reader reader) throws SQLException {
        checkOpen();
        try {
            ((CallableStatement)getDelegate()).setNClob(parameterName, reader);
        }
        catch (final SQLException e) {
            handleException(e);
        }
    }

    @Override
    public <T> T getObject(final int parameterIndex, final Class<T> type)
            throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getObject(parameterIndex, type);
}
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }

    @Override
    public <T> T getObject(final String parameterName, final Class<T> type)
            throws SQLException {
        checkOpen();
        try {
            return ((CallableStatement)getDelegate()).getObject(parameterName, type);
        }
        catch (final SQLException e) {
            handleException(e);
            return null;
        }
    }


}
