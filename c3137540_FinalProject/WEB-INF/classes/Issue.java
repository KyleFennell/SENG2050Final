import java.sql.Timestamp;
import java.util.List;

public class Issue {
	private int issueID;
	private String title;
	private String description;
	private String resolutionDetails;
	//private Date reportedDateTime;
	//private Date resolvedDateTime;
	private Timestamp reportedDateTime;
	private Timestamp resolvedDateTime;
	private int userID;
	private int ITStaffID;
	private String status;
	private List<Comment> comments;
	private List<String> keywords;
	private String category;
	private String subCategory;
	
	public int getIssueID() {
		return issueID;
	}
	public void setIssueID(int id) {
		this.issueID = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String s) {
		this.title = s;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String desc) {
		this.description = desc;
	}
	public String getResolutionDetails() {
		return resolutionDetails;
	}
	public void setResolutionDetails(String details) {
		this.resolutionDetails = details;
	}
	public Timestamp getReportedDateTime() {
		return reportedDateTime;
	}
	public void setReportedDateTime(Timestamp reported) {
		this.reportedDateTime = reported;
	}
	public Timestamp getResolvedDateTime() {
		return resolvedDateTime;
	}
	public void setResolvedDateTime(Timestamp resolved) {
		this.resolvedDateTime = resolved;
	}
	/*public Date getReportedDateTime() {
		return reportedDateTime;
	}
	public void setReportedDateTime(Date reported) {
		this.reportedDateTime = reported;
	}
	public Date getResolvedDateTime() {
		return resolvedDateTime;
	}
	public void setResolvedDateTime(Date resolved) {
		this.resolvedDateTime = resolved;
	}*/
	public int getUserID() {
		return userID;
	}
	public void setUserID(int id) {
		userID = id;
	}
	public int getITStaffID() {
		return ITStaffID;
	}
	public void setITStaffID(int id) {
		ITStaffID = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String stat) {
		this.status = stat;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> s) {
		this.comments = s;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> words) {
		this.keywords = words;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String cat) {
		this.category = cat;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String sub) {
		this.subCategory = sub;
	}
}
