package net.redirectme.apps;

public class SessionModel {
	private long id;
	private String  name;
	private String  author;
	private String  organization;
	private String  track;
	private String  location;
	private String  date;	
	private String  starttime;
	private String  closetime;
	private String  summary;
	private String  comments;	
	private String  media;
	private String  tag;
	private String  rating;
	private String 	phone;
	private String 	email;
	private String 	remind;


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}



	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String org) {
		this.organization = org;
	}	
	/**
	 * @return the track
	 */
	public String getTrack() {
		return track;
	}

	/**
	 * @param track the track to set
	 */
	public void setTrack(String track) {
		this.track = track;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param startdate the startdate to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the starttime
	 */
	public String getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return the closetime
	 */
	public String getClosetime() {
		return closetime;
	}

	/**
	 * @param closetime the closetime to set
	 */
	public void setClosetime(String closetime) {
		this.closetime = closetime;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comment) {
		this.comments = comment;
	}

	/**
	 * @return the media
	 */
	public String getMedia() {
		return media;
	}

	/**
	 * @param media the media to set
	 */
	public void setMedia(String media) {
		this.media = media;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * @return the rate
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemind() {
		return remind;
	}

	public void setRemind(String remind) {
		this.remind = remind;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return name+author+id;
	}

	public String[] getStartTimeSplit(){
		String[]hm = starttime.split(":");
		return hm;
	}
	public String[] getStartDateSplit(){
		String[]ymd = date.split("/");
		return ymd;
	}

	public int getTrackIcon()
	{
		int trackIcon = R.drawable.track_43_general;
		if(getTrack().equalsIgnoreCase("General"))
			trackIcon = R.drawable.track_43_general;
		if(getTrack().equalsIgnoreCase("Tech Showcase"))
			trackIcon = R.drawable.track_48_tech_showcase;
		if(getTrack().equalsIgnoreCase("Application Development"))
			trackIcon = R.drawable.track_20_app_dev;
		if(getTrack().equalsIgnoreCase("Platform Development"))
			trackIcon = R.drawable.track_21_platform_dev;
		if(getTrack().equalsIgnoreCase("IVI"))
			trackIcon = R.drawable.track_28_ivi;
		if(getTrack().equalsIgnoreCase("TV"))
			trackIcon = R.drawable.track_46_tv;
		if(getTrack().equalsIgnoreCase("Tutorials & Community"))
			trackIcon = R.drawable.track_45_tut_and_community;
		if(getTrack().equalsIgnoreCase("Other"))
			trackIcon = R.drawable.track_24_other;
		if(getTrack().equalsIgnoreCase("Wearable"))
			trackIcon = R.drawable.track_21_wearable;
		if(getTrack().equalsIgnoreCase("Ecosystem"))
			trackIcon = R.drawable.track_42_ecosystem;
		if(getTrack().equalsIgnoreCase("IoT"))
			trackIcon = R.drawable.track_44_iot;
		return trackIcon;
	}



}
