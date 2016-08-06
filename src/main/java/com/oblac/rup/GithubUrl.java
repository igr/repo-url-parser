package com.oblac.rup;

public class GithubUrl {

	private static final String GITHUB = "github";
	private static final String GITHUB_DOT_COM = "github.com";
	private static final String GIT = "git";

	/**
	 * Parses Github URL.
	 * @see #parse(String)
	 */
	public static GithubRepo gh(String repoUrl) {
		return parse(repoUrl);
	}

	/**
	 * Parses Github URL.
	 */
	public static GithubRepo parse(String repoUrl) {
		if (repoUrl == null) {
			return null;
		}

		GithubRepo gh = new GithubRepo();

		int ndx = repoUrl.indexOf("://");

		if (ndx == -1) {
			ndx = repoUrl.indexOf('/');

			if (ndx == -1) {
				return null;
			}

			// *********************
			// shorthand, mediumhand
			// *********************

			String user = repoUrl.substring(0, ndx);
			String repo = repoUrl.substring(ndx + 1);
			String branch = "master";

			// HOST

			gh.host = GITHUB_DOT_COM;

			// USER

			ndx = user.indexOf(':');

			if (ndx > 0) {
				String protocol = user.substring(0, ndx);
				user = user.substring(ndx + 1);

				ndx = protocol.indexOf('@');

				if (ndx > 0) {
					String pUser = protocol.substring(0, ndx);
					String pHost = protocol.substring(ndx + 1);

					if (!pUser.equals(GIT)) {
						return null;
					}
					if (!pHost.equals(GITHUB_DOT_COM)) {
						gh.enterprise = true;
					}

					gh.host = pHost;

					repo = stripDotGit(repo);
				}
				else {
					if (!protocol.equals(GITHUB)) {
						return null;
					}
				}
			}

			gh.user = user;

			// REPO + BRANCH

			ndx = repo.indexOf('#');

			if (ndx > 0) {
				branch = repo.substring(ndx + 1);
				repo = repo.substring(-0, ndx);
			}

			gh.repo = repo;
			gh.branch = branch;

			return gh;
		}

		// ******
		// common
		// ******

		gh.protocol = repoUrl.substring(0, ndx);

		repoUrl = repoUrl.substring(ndx + 3);

		String parts[] = repoUrl.split("/");

		if (parts.length < 3) {
			return null;
		}

		gh.host = parts[0];
		if (!gh.host.equals(GITHUB_DOT_COM)) {
			gh.enterprise = true;
		}

		gh.user = parts[1];
		gh.repo = stripDotGit(parts[2]);

		String branch = "master";

		if (parts.length >= 5) {
			if (parts[3].equals("tree")) {
				branch = parts[4];
				for (int i = 5; i < parts.length; i++) {
					branch += '/' + parts[i];
				}
			}
			else if (parts[3].equals("blob")) {
				if (!isChecksum(parts[4])) {
					branch = parts[4];
				}
			}
		}

		gh.branch = branch;

		return gh;
	}


	/**
	 * Strips ".git" from the strings end.
	 */
	private static String stripDotGit(String name) {
		if (name.endsWith(".git")) {
			name = name.substring(0, name.length() - 4);
		}
		return name;
	}

	/**
	 * Returns {@code true} if given string is a checksum.
	 */
	private static boolean isChecksum(String checksum) {
		if (checksum.length() != 40) {
			return false;
		}
		for (int i = 0; i < 40; i++) {
			char c = checksum.charAt(i);

			if ((c < '0' || c > '9') && (c < 'a' || c > 'f')) {
				return false;
			}
		}
		return true;
	}

}