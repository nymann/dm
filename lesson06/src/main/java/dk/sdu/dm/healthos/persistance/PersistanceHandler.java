package dk.sdu.dm.healthos.persistance;

import dk.sdu.dm.healthos.domain.Employee;
import dk.sdu.dm.healthos.domain.Patient;
import dk.sdu.dm.healthos.domain.Admission;
import dk.sdu.dm.healthos.domain.Bed;
import dk.sdu.dm.healthos.domain.IPersistanceHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Nordestgaard | olnor18
 */
public class PersistanceHandler implements IPersistanceHandler {
    private static PersistanceHandler instance;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "health_os";
    private String username = "dm_user";
    private String password = "thisisit";
    private Connection connection = null;

    private PersistanceHandler() {
        initializePostgresqlDatabase();
    }

    public static PersistanceHandler getInstance() {
        if (instance == null) {
            instance = new PersistanceHandler();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + url + ":" + port + "/" + databaseName,
                    username,
                    password);
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) System.exit(-1);
        }
    }

    @Override
    public List<Employee> getEmployees() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees");
            ResultSet sqlReturnValues = stmt.executeQuery();
            int rowcount = 0;
            List<Employee> returnValue = new ArrayList<>();
            while (sqlReturnValues.next()) {
                returnValue.add(new Employee(
                        sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2),
                        sqlReturnValues.getInt(3),
                        sqlReturnValues.getInt(4),
                        sqlReturnValues.getInt(5),
                        sqlReturnValues.getInt(6)));
            }
            return returnValue;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee getEmployee(int id) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
            stmt.setInt(
                    1,
                    id);
            ResultSet sqlReturnValues = stmt.executeQuery();
            if (!sqlReturnValues.next()) {
                return null;
            }
            return new Employee(
                    sqlReturnValues.getInt(1),
                    sqlReturnValues.getString(2),
                    sqlReturnValues.getInt(3),
                    sqlReturnValues.getInt(4),
                    sqlReturnValues.getInt(5),
                    sqlReturnValues.getInt(6));
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /*
    Implement all of the following. Beware that the model classes are as of yet not properly implemented
    */

    @Override
    public boolean createEmployee(Employee employee) {
        var insertText = "INSERT INTO employess (name, phone, position_id, department_id, room_id) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement insertStatement = connection.prepareStatement(insertText);
            insertStatement.setString(1, employee.getName());
            insertStatement.setInt(2, employee.getPhone());
            insertStatement.setInt(3, employee.getPosition_id());
            insertStatement.setInt(4, employee.getDepartment_id());
            insertStatement.setInt(5, employee.getRoom_id());
            return insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Patient> getPatients() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Patient getPatient(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean createPatient(Patient patient) {
        //make HealthOS support this action in the presentation layer too.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Bed> getBeds() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Bed getBed(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean createBed(Bed bed) {
        //make HealthOS support this action in the presentation layer too.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Admission> getAdmissions() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Admission getAdmission(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean createAdmission(Admission admission) {
        //make HealthOS support this action in the presentation layer too.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean deleteAdmission(int id) {
        //make HealthOS support this action in the presentation layer too.
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
