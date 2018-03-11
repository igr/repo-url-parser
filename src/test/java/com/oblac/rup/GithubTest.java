package com.oblac.rup;

import org.junit.jupiter.api.Test;

import static com.oblac.rup.GithubUrl.gh;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GithubTest {

	@Test
	public void testShorthand_userRepo() {
		GithubRepo gh = gh("user/repo");

		assertEquals("github.com", gh.host);
		assertEquals("user", gh.user);
		assertEquals("repo", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testShorthand_userRepoBranch() {
		GithubRepo gh = gh("user/repo#branch");

		assertEquals("github.com", gh.host);
		assertEquals("user", gh.user);
		assertEquals("repo", gh.repo);
		assertEquals("branch", gh.branch);
	}

	@Test
	public void testMediumhand_userRepo() {
		GithubRepo gh = gh("github:user/repo");

		assertEquals("github.com", gh.host);
		assertEquals("user", gh.user);
		assertEquals("repo", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testMediumhand_userRepoBranch() {
		GithubRepo gh = gh("github:user/repo#branch");

		assertEquals("github.com", gh.host);
		assertEquals("user", gh.user);
		assertEquals("repo", gh.repo);
		assertEquals("branch", gh.branch);
	}

	@Test
	public void testRejectBitbucket() {
		GithubRepo gh = gh("bitbucket:user/repo");

		assertNull(gh);
	}

	@Test
	public void testOldSchool() {
		GithubRepo gh = gh("git@github.com:heroku/heroku-flags.git");

		assertEquals("github.com", gh.host);
		assertEquals("heroku", gh.user);
		assertEquals("heroku-flags", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testProtocol_gitHttps() {
		GithubRepo gh = gh("git+https://github.com/foo/bar.git");

		assertEquals("git+https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("foo", gh.user);
		assertEquals("bar", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testProtocol_git() {
		GithubRepo gh = gh("git://github.com/foo/bar.git");

		assertEquals("git", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("foo", gh.user);
		assertEquals("bar", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testGithubEnterprise_gitat() {
		GithubRepo gh = gh("git@ghe.example.com:heroku/heroku-flags.git");

		assertEquals("ghe.example.com", gh.host);
		assertEquals("heroku", gh.user);
		assertEquals("heroku-flags", gh.repo);
		assertEquals("master", gh.branch);
		assertTrue(gh.enterprise);
	}

	@Test
	public void testGithubEnterprise_git() {
		GithubRepo gh = gh("git://ghe.example.com/foo/bar.git");

		assertEquals("ghe.example.com", gh.host);
		assertEquals("foo", gh.user);
		assertEquals("bar", gh.repo);
		assertEquals("master", gh.branch);
		assertTrue(gh.enterprise);
	}

	@Test
	public void testHttp() {
		GithubRepo gh = gh("http://github.com/oblac/jodd.git");

		assertEquals("http", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testHttp_enterprise() {
		GithubRepo gh = gh("http://gh.com/oblac/jodd.git");

		assertEquals("http", gh.protocol);
		assertEquals("gh.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("master", gh.branch);
		assertTrue(gh.enterprise);
	}

	@Test
	public void testHttps() {
		GithubRepo gh = gh("https://github.com/oblac/jodd.git");

		assertEquals("https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testHttps_enterprise() {
		GithubRepo gh = gh("https://gh.com/oblac/jodd.git");

		assertEquals("https", gh.protocol);
		assertEquals("gh.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("master", gh.branch);
		assertTrue(gh.enterprise);
	}

	@Test
	public void testHttps_noGitExtension() {
		GithubRepo gh = gh("https://github.com/oblac/jodd");

		assertEquals("https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testHttps_branch() {
		GithubRepo gh = gh("https://github.com/oblac/jodd/tree/branch");

		assertEquals("https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("branch", gh.branch);
	}

	@Test
	public void testHttps_branchWithSlash() {
		GithubRepo gh = gh("https://github.com/oblac/jodd/tree/feature/branch");

		assertEquals("https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("feature/branch", gh.branch);
	}

	@Test
	public void testHttps_branchWithDot() {
		GithubRepo gh = gh("https://github.com/oblac/jodd/tree/2.1");

		assertEquals("https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("2.1", gh.branch);
	}

	@Test
	public void testHttps_blob() {
		GithubRepo gh = gh("https://github.com/oblac/jodd/blob/123456789012345678901234567890abcdefabcd/app.json");

		assertEquals("https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("master", gh.branch);
	}

	@Test
	public void testHttps_blobWithBranch() {
		GithubRepo gh = gh("https://github.com/oblac/jodd/blob/branch/.gitignore");

		assertEquals("https", gh.protocol);
		assertEquals("github.com", gh.host);
		assertEquals("oblac", gh.user);
		assertEquals("jodd", gh.repo);
		assertEquals("branch", gh.branch);
	}

	@Test
	public void testStrings() {
		GithubRepo gh = gh("oblac/jodd");

		assertEquals("https://api.github.com/repos/oblac/jodd/tarball/master", gh.toTarballUrl());
		assertEquals("https://api.github.com/repos/oblac/jodd/archive/master.zip", gh.toZipUrl());
		assertEquals("https://api.github.com/repos/oblac/jodd", gh.toApiUrl());
		assertEquals("https://github.com/oblac/jodd", gh.toWebUrl());
		assertEquals("https://travis-ci.org/oblac/jodd", gh.toTravisUrl());
	}

	@Test
	public void testStrings_withBranch() {
		GithubRepo gh = gh("oblac/jodd#branch");

		assertEquals("https://api.github.com/repos/oblac/jodd/tarball/branch", gh.toTarballUrl());
		assertEquals("https://api.github.com/repos/oblac/jodd/archive/branch.zip", gh.toZipUrl());
		assertEquals("https://api.github.com/repos/oblac/jodd", gh.toApiUrl());
		assertEquals("https://github.com/oblac/jodd/blob/branch", gh.toWebUrl());
		assertEquals("https://travis-ci.org/oblac/jodd?branch=branch", gh.toTravisUrl());
	}

	@Test
	public void testStrings_enterprise() {
		GithubRepo gh = gh("https://foo.com/oblac/jodd");

		assertEquals("https://foo.com/api/v3/repos/oblac/jodd/tarball/master", gh.toTarballUrl());
		assertEquals("https://foo.com/api/v3/repos/oblac/jodd/archive/master.zip", gh.toZipUrl());
		assertEquals("https://foo.com/api/v3/repos/oblac/jodd", gh.toApiUrl());
		assertEquals("https://foo.com/oblac/jodd", gh.toWebUrl());
	}

	@Test
	public void testNull() {
		assertNull(gh(null));
		assertNull(gh(""));
	}

}