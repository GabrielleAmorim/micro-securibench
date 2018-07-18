/**
 * @author Benjamin Livshits <livshits@cs.stanford.edu>
 * 
 * $Id: Basic29.java,v 1.4 2006/04/04 20:00:40 livshits Exp $
 */
package securibench.micro.basic;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import securibench.micro.BasicTestCase;
import securibench.micro.MicroTestCase;

/**
 * @servlet description="recursive data structures"
 * @servlet vuln_count = "2"
 */
public class Basic29 extends BasicTestCase implements MicroTestCase {
    class Node {
        String value;
        Node   next = null;
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name"); //source high
        Node head = new Node();
        Node next = new Node();
        head.next = next;
        next.value = name;
        
        PrintWriter writer = resp.getWriter();
        writer.println(next.value);                  /* BAD */ //sink low
        writer.println(head.next.value);             /* BAD */ //sink low
        writer.println(head.value);                  /* OK */ //sink low
    }

    public String getDescription() {
        return "recursive data structures";
    }

    public int getVulnerabilityCount() {
        return 2;
    }
    
    public static void main(String[] args) {
		Basic29 b = new Basic29();
		try {
			b.doGet(null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}