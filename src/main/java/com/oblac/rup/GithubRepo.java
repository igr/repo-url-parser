package com.oblac.rup;

public class GithubRepo {

	/**
	 * Returns GitHub user.
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Returns GitHub repo name.
	 */
	public String getRepo() {
		return repo;
	}

	/**
	 * Returns branch name.
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * Returns host name.
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Returns used protocol. May be {@code null} if not specified.
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * Returns {@code true} if it is enterprise mode.
	 */
	public boolean isEnterprise() {
		return enterprise;
	}

	/**
	 * Converts to API url.
	 */
	public String toApiUrl() {
		if (isEnterprise()) {
			return "https://" + host + "/api/v3/repos/" + user + "/" + repo;
		}
		return "https://api.github.com/repos/" + user + "/" + repo;
	}

	/**
	 * Creates tarball URL.
	 */
	public String toTarballUrl() {
		return toApiUrl() + "/tarball/" + branch;
	}

	/**
	 * Creates zip URL.
	 */
	public String toZipUrl() {
		return toApiUrl() + "/archive/" + branch + ".zip";
	}

	/**
	 * Creates web url.
	 */
	public String toWebUrl() {
		String url = "https://" + host + "/" + user + "/" + repo;

		if (!branch.equals("master")) {
			url += "/blob/" + branch;
		}

		return url;
	}

	/**
	 * Creates Travis url.
	 */
	public String toTravisUrl() {
		String url = "https://travis-ci.org/" + user + "/" + repo;

		if (!branch.equals("master")) {
			url += "?branch=" + branch;
		}

		return url;
	}

	String user;
	String repo;
	String branch;
	String host;
	String protocol;
	boolean enterprise;
}
