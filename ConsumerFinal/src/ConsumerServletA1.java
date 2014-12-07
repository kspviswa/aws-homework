

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map.Entry;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
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
 * Servlet implementation class ConsumerServletA1
 */
public class ConsumerServletA1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	AWSCredentials credentials;
	BasicAWSCredentials awsCreds;
	AmazonSQS sqs;
	Region reg;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConsumerServletA1() {
		super();
		//System.setProperty("socksProxyHost", "localhost");
		//System.setProperty("socksProxyPort", "1025");

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		credentials = null;
		awsCreds = null;
		try {
			//credentials = new ProfileCredentialsProvider("Gomathi").getCredentials();
			// Key masked for security purpose
			awsCreds = new BasicAWSCredentials("access_key", "secret-key");
		} catch (Exception e) {
			//throw new AmazonClientException(
			response.getWriter().println( "Cannot load the credentials from the credential profiles file. " +
							"Please make sure that your credentials file is at the correct " +
							"location (C:\\Users\\Viswa KSP\\.aws\\credentials), and is in valid format." + e.getLocalizedMessage());
		}
		//sqs = new AmazonSQSClient(credentials);
		sqs = new AmazonSQSClient(awsCreds);
		reg = Region.getRegion(Regions.US_EAST_1);
		sqs.setRegion(reg);

		String myQueueUrl;
		myQueueUrl = "https://sqs.us-east-1.amazonaws.com/663952165329/ECIIIAssignment1";

		response.getWriter().println("<h2> Connected to Amazon SQS -  ECIIIAssignment1</h2>");
		
		//response.getWriter().println("<h3><I><u> URL : " + myQueueUrl + "</u></I></h3><br>");

		{

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
					else
					{
						response.getWriter().println("No messages available in the sqs");
					}
					break;
				}
				if(bFlag == false)
				{
					response.getWriter().println("<p> <h3> queried @" + now + "</h3><br>");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
