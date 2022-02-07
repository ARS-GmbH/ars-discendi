# Contributing to ars discendi

This page provides information about contributing code to the ars discendi project.

## Engaging in the Project

* If you are a new contributor, see: [Steps to Contribute](#steps-to-contribute)

* Before opening a new pull request, create an issue describing the problem you want to solve or the code that should be enhanced. Please note if you are willing to work on that issue.

* Please provide as much context as possible when you open an issue. The information you provide must be comprehensive enough to reproduce that issue for the assignee. 

* When creating an issue, try using one of our issue templates which already contain some guidelines on which content is expected to process the issue most efficiently. If no template applies, you can of course also create an issue from scratch.

* The maintainer will review the issue and decide whether it should be implemented as a Pull Request. In that case and if you're willing to work on it, he will assign the issue to you. If the maintainer decides against picking up the issue, it will be closed with a proper explanation.

* Relevant coding style guidelines are available at https://google.github.io/styleguide/.

## Steps to Contribute

Should you wish to work on an existing issue, please claim it first by commenting on the GitHub issue that you would like to work on. This is to prevent duplicated efforts from other contributors on the same issue.

Only start working on the Pull Request after the maintainer assigned the issue to you to avoid unnecessary efforts.

If you have questions about one of the issues, please comment on them, and one of the maintainers will clarify.

Contributions must be licensed under the [GNU General Public License v3.0](./LICENSE)

### Opening Pull Request

1. Commit your changes and push them to your fork on GitHub.
    * Commits should be as small as possible while ensuring that each commit is correct independently (i.e., each commit should compile and pass tests).
    * Test your changes as thoroughly as possible before you commit them. Preferably, automate your test by unit/integration tests.
    * Pull Requests should only contain one feature, code style changes or additional features should be proposed in a separate Pull Request
2. In the GitHub Web UI, click the _New Pull Request_ button.
3. Select `ars-discendi` as _base fork_ and `master` as _base_, then click _Create Pull Request_.
4. Fill in the Pull Request description according to the [proposed template](./.github/PULL_REQUEST_TEMPLATE.md).
5. Click _Create Pull Request_.
6. Wait for CI/GitHub Checks results/reviews.
7. Process the feedback. If there are changes required, commit them in your local branch and push them again to GitHub. Your pull request will be updated automatically.


### Reviewing a Pull Request

* Every comment on the pull request should be accepted as a change request and should be discussed. When something is optional, it should be noted in the comment.
* When a change is requested and a conversation is opened, only the reviewer that opened the conversation should be allowed to resolve it, which is done when the reviewer is settled with the answer/change.
* When some requested change is implemented by the creator, ideally the link should be posted to the commit where the change has happened.
* A reviewer should resolve all conversations that she/he has started before approving the pull request.
* Proposed changelog entries must be correct
* Proper changelog labels must be set so that the release notes can be generated automatically in the pre-release
* The pull request will be merged by a maintainer after it has been approved by a pull request reviewer or maintainer

## Copyright

ars discendi is licensed under [GNU General Public License v3.0](./LICENSE).
We consider all contributions as GPLv3 unless it's explicitly stated otherwise.
GPLv3-incompatible code contributions will be rejected.

We **Do NOT** require pull request submitters to sign the [contributor agreement]
as long as the code is licensed under GPLv3, and merged by one of the contributors with the signed agreement.

We still encourage people to sign the contributor agreement if they intend to submit more than a few pull requests.
Signing is also a mandatory prerequisite for getting merge/push permissions as a maintainer.
