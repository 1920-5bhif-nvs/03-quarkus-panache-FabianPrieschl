package at.htl.quickstart;

import io.quarkus.test.junit.QuarkusTest;
import org.assertj.db.type.Changes;
import org.assertj.db.type.Request;
import org.assertj.db.type.Table;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.sql.DataSource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.db.api.Assertions.assertThat;

@QuarkusTest
public class DatabaseTest {

    @Inject
    DataSource dataSource;

    @Test
    public void test01_data_of_table_movie_correct(){
        Table movie = new Table(this.dataSource, "movie");
        assertThat(movie)
                .column("genre")
                .hasValues("Drama", "Drama", "Action");

        assertThat(movie).hasNumberOfRows(3);
    }

    @Test
    public void test02_requests(){
        Request request1 = new Request(this.dataSource,
                "select title, genre from movie where id = 1 or id = 2");

        assertThat(request1).row(0).value(1).equals("Drama");
        assertThat(request1).column(1).value(0).equals("Drama");
    }
}
