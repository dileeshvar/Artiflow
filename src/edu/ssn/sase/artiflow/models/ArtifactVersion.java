package edu.ssn.sase.artiflow.models;

public class ArtifactVersion {
	private int artifactVersionId;
	private int artifactId;
	private int versionNo;

	public int getArtifactVersionId() {
		return artifactVersionId;
	}
	public void setArtifactVersionId(int artifactVersionId) {
		this.artifactVersionId = artifactVersionId;
	}
	public int getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(int artifactId) {
		this.artifactId = artifactId;
	}
	public int getVersionNo() {
		return versionNo;
	}
	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}
}
