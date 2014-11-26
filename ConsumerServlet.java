

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.TimerTask;
import java.util.Date;
import java.util.Timer;


/**
 * Servlet implementation class ConsumerServlet
 */
public class ConsumerServlet extends HttpServlet implements Runnable {
	private static final long serialVersionUID = 1L;

	AWSCredentials credentials;
    AmazonSQS sqs;
    Region reg;
    boolean bInited = false;
    volatile boolean bAfterGet = false;
    HttpServletRequest requestL;
    HttpServletResponse responseL;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConsumerServlet() {
        super();
        // TODO Auto-generated constructor stub        
    }

    public void run() {
    	
    	while(bAfterGet == false);
    	
    	while(true)
    	{
    		try{
    		doPost(requestL,responseL);
    		Thread.currentThread().sleep(1000);
    		}
    		catch(Exception e)
    		{
    			
    		}
    		
    		
    	}
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().println();
		//response.getWriter().println("Hello Boss");
		//doPost(request, response);
		
		credentials = null;
        try {
            credentials = new ProfileCredentialsProvider("Gomathi").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (C:\\Users\\Viswa KSP\\.aws\\credentials), and is in valid format.",
                    e);
        }

        sqs = new AmazonSQSClient(credentials);
        reg = Region.getRegion(Regions.US_EAST_1);
        sqs.setRegion(reg);
        
        requestL = request;
        responseL = response;
        
        bAfterGet = true;
        
        run();
        //while(true)
        //{
        	//doPost(request,response);
        	//try{
        	//Thread.currentThread().sleep(2000);
        	//}
        	//catch(Exception e)
        	//{
        		
        	//}
        //}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().print(“Hello World!");
		//response.getWriter().println();
		//response.getWriter().println("Hello Boss Viswa");
		
        CreateQueueRequest createQueueRequest = new CreateQueueRequest("Consumer-Q-Test");
        String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
        
        // Receive messages
        
        if(bInited == false)
        {
        
        	response.getWriter().println("<html><body><h2> Connected to Amazon SQS </h2>");
    	    response.getWriter().println("<I><u> URL : " + myQueueUrl + "</u></I><br>");
    	    
    	    bInited = true;
        }
        else
        {
        	response.flushBuffer();
        }
        Date now = new Date();
        boolean bFlag = false, bEnd = false;
        while(true)
        {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
        
        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        if(messages.isEmpty())
        {
        	if(bEnd == true)
        	{
        		response.getWriter().println("</table>");
        	}
        	break;
        }
        
        if(bFlag == false)
        {
        	response.getWriter().println("<p> @" + now + "<br>");
        	bFlag = true;
        	response.getWriter().println("<table border='5'><tr><th>Handle</th><th>Body</th></tr>");
        	bEnd = true;
        }
        
        
        for (Message message : messages) {
        	response.getWriter().println("<tr><td>" + message.getMessageId()+"</td>");
        	response.getWriter().println("<td>" + message.getBody()+"</td></tr>");
        }
        }
	}

}
