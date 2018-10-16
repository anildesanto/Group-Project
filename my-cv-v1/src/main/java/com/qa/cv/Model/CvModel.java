package com.qa.cv.Model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


	@Entity
	@Table(name = "CV")
	@EntityListeners(AuditingEntityListener.class)
	@JsonIgnoreProperties(value =  {"creationDate", "lastModified"}, allowGetters =true)
	public class CvModel implements Serializable {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long cvId;

		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "userID", nullable = false)
		@OnDelete(action = OnDeleteAction.CASCADE)
		@JsonIgnore
		private UsersDataModel userID;

		@NotBlank
		private String cvLink;

		@Column(nullable = false, updatable=false)
		@Temporal(TemporalType.TIMESTAMP)
		@CreatedDate
		private Date creationDate;
		
		@Column(nullable = false, updatable = false)
		@Temporal(TemporalType.TIMESTAMP)
		@LastModifiedDate
		private Date lastModified;
		
		@NotBlank
		private String status;
		
		@ManyToOne(fetch = FetchType.LAZY, optional = false)
		@JoinColumn(name = "departmentID", nullable = false)
		@OnDelete(action = OnDeleteAction.CASCADE)
		@JsonIgnore
		private DepartmentModel departmentID;

		public CvModel() {

		}

		public CvModel(UsersDataModel i, String cvLink, String status, DepartmentModel departmentID) {
			this.userID = i;
			this.cvLink = cvLink;
			this.status = status;
			this.departmentID = departmentID;
		}

		public Long getCvId() {
			return cvId;
		}

		public void setCvId(Long cvId) {
			this.cvId = cvId;
		}

		public UsersDataModel getUserID() {
			return userID;
		}

		public void setUserID(UsersDataModel userID) {
			this.userID = userID;
		}

		public String getCvLink() {
			return cvLink;
		}

		public void setCvLink(String cvLink) {
			this.cvLink = cvLink;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public DepartmentModel getDepartmentID() {
			return departmentID;
		}

		public void setDepartmentID(DepartmentModel departmentID) {
			this.departmentID = departmentID;
		}


}
