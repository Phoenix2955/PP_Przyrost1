package example;

import org.junit.Test;

import static org.junit.Assert.*;

public class SqlValidatorTest {

    private SqlValidator sqlValidator = new SqlValidator();

    @Test
    public void simpleSelect() {
        assertTrue(sqlValidator.validate("SELECT from xxxxxxx where ppppppp"));
    }

    

}