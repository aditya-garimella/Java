package com.interactivebrokers.trader.web.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.interactivebrokers.trader.web.model.Instrument;

public class InstrumentRowMapper implements RowMapper<Instrument>{

		@Override
		public Instrument mapRow(ResultSet rs, int rowNum) throws SQLException {
			Instrument instrument = new Instrument();
			instrument.setSymbolID(rs.getString("symbol_id"));
			instrument.setInstrumentDesc(rs.getString("instrument_desc"));
			instrument.setPrice(rs.getDouble("price"));

			return instrument;
		}
}
