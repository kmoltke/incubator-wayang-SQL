package org.apache.wayang.api.sql;

import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.wayang.api.sql.calcite.utils.ModelParser;
import org.apache.wayang.api.sql.context.SqlContext;
import org.apache.wayang.core.api.Configuration;
import org.json.simple.parser.ParseException;

import org.apache.wayang.basic.data.Record;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

public class SqlDemo {
    public static void main(String[] args) throws IOException, ParseException, SQLException, SqlParseException {
        Configuration configuration = new ModelParser(new Configuration()).setProperties();

        configuration.setProperty("wayang.fs.table.url", "C:/tmp/data/movie.csv");

        SqlContext sqlContext = new SqlContext(configuration);

        Collection<Record> result = sqlContext.executeSql(
                "SELECT m.id, mg.movieid, g.genre, m.title, m.\"year\" " +
                "FROM fs.movie m " +
                "JOIN postgres.movie_genre mg on m.id = mg.movieid " +
                "JOIN postgres.genre g on mg.genre = g.genre"
        );



        printResults(20, result);

    }

    private static void printResults(int n, Collection<Record> result) {
        // print up to n records
        int count = 0;
        Iterator<Record> iterator = result.iterator();
        while (iterator.hasNext() && count++ < n) {
            Record record = iterator.next();
            System.out.print(" | ");
            for (int i = 0; i < record.size(); i++) {
                Object val = record.getField(i);
                if (val == null) { System.out.print(" " + " | "); }
                else System.out.print(val.toString() + " | ");
            }
            System.out.println("");
        }
    }
}
