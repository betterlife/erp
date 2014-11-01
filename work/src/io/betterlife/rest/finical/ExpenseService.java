package io.betterlife.rest.finical;

import io.betterlife.application.Config;
import io.betterlife.domains.finical.Expense;
import io.betterlife.persistence.finical.ExpenseOperator;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.ObjectMapper;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Author: Lawrence Liu(xqinliu@cn.ibm.com)
 * Date: 11/1/14
 */
@Path(value="/expense")
@Stateless
public class ExpenseService {

    @PersistenceContext(unitName = Config.PersistenceUnitName)
    private EntityManager entityManager;

    @GET
    @Path("/{expenseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getExpense(@PathParam("expenseId") long expenseId) throws IOException {
        Expense expense = ExpenseOperator.getInstance().get(entityManager, expenseId);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(expense);
    }
}
