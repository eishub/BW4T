#Blocks World for Teams - v3.5.0
Blocks World for Teams (BW4T) is a testbed for team coordination. BW4T allows for games with human-human, agent-agent and human-agent teams of variable sizes. The goal is to jointly deliver a sequence of colored blocks in a particular order as fast as possible. A complicating factor is that the players cannot see each other.

[[Self-explaining Agents - A Study in the BW4T Testbed for Team Coordination]](http://www.dtic.mil/dtic/tr/fulltext/u2/a550537.pdf)

#  Maven
### First time running Maven
Change your current directory to `${repository_root}/bw4t`
 > cd ./bw4t/
 
First compile and install the `bw4t-core` to your local maven repository.
 > mvn -pl bw4t-core install

Then test or install the rest of `bw4t` as follows:
 > mvn test
 
### Updating the version in the pom file (making a new release)
Change your current working directory to `${repository_root}/bw4t`
 > cd ./bw4t/

Make sure you have the newest master
 > git checkout master
 > git pull

Make a new branch to update the version of the branch
 > git checkout -b master_release
 > git push --set-upstream origin master_release

Use maven to update the pom files, replace `${version-number}` with the new version number
 > mvn versions:set "-DnewVersion=${version-number}"

Ensure that the system will still build with the changed pom files
 > mvn clean install

If no errors occur we can remove the backups of the old pom files
 > mvn versions:commit

Upload your changes to github and create a pull request
 > git commit -a -m "Releasing version ${version-number}!"
 > git push

The local branch can now be deleted
 > git checkout master
 > git branch -D master_release

#  GitHub <i class="icon-provider-github"></i>
The **BW4T Git Repository** can be found at [GitHub](https://github.com/MartinRogalla/BW4T/).
### <i class="icon-attention"></i>Git Rules
1. <i class="icon-lock"></i>*Never* push anything to the **master** branch. 
 > If you want to work on something as a **group**: [create a new branch][6], commit and add your changes and [create a pull request][5]. Only the git master is allowed to merge the changes to the master branch. **All other unauthorized commits on the master branch will be reverted.**
2. <i class="icon-fork"></i>*Never* branch off of the **legacy** branch.
 > Branch off of the *branch created by your group* or the *master branch* instead. The legacy branch 

3. <i class="icon-trash"></i> If you're done with your branch, *delete the branch*.
 >You can delete your local branch with `git branch -D BranchName`, where *BranchName* is the name of your branch. You can delete the remote branch by pushing the branch and putting a colon in front of the branch as such: `git push origin :BranchName`, where *BranchName* is the name of your branch.

4. <i class="icon-flash"></i> Never perform a *force-push*.
 >Never try to rewrite history in Git. This should be avoided at all costs. If you want to revert a specific commit, use the [`git revert`][4]. 

5. <i class="icon-fork"></i> If your branch is a second level branch, prepend the first level branch name.
 >So for example: in Group 1 we created a branch called `development` to do the refactoring. If I want to add some tests to the client and make a branch called `client_testing`, then the **actual name** has to be called: `development_client_testing`. 

### <i class="icon-help"></i>Git FAQ

 - Help! I accidentally changed a file, which I didn't intend to change. How do I get a copy of the file, as it was before?
 > Run [`git checkout -- /path/to/file/`][1] on the file you want to revert the changes. *Tip: accidentally changed all the classpath files and are you lazy enough to not checkout every single .classpath file? Make use of the wildcard like this: `git checkout -- *.classpath`*

 - How do I see what file(s) I changed?
>Run `git status`.

 - Think something should be added to this FAQ?
>Send Martin an e-mail at `jorn.rogalla@gmail.com`

For more information on Git have a look on [**StackOverflow**][2] or learn some great workflows via the tutorials at [**Atlassian**][3].

# Jenkins <i class="icon-flag-checkered"></i>
**Integration Testing** is done by Jenkins on our [Integration Server](https://martinrogalla.com/jenkins/). Whenever pushes to certain branches are made, the changes are pulled by the Integration Server. The Integration Server then runs all the tests via Maven and publishes the test results.
### <i class="icon-attention"></i>Jenkins Rules
 - <i class="icon-attention-alt"></i> It is *never* okay to have a build **fail**.
 > To prevent this, make sure you **run Maven before you push to a branch** *(`mvn clean install` or in Eclipse: Run As -> Maven Build...(goals: `clean install`))*. In the case that you do have a failing build, **make it your first priority, to fix the build**, before you continue any other work.(*reverting if necessary*)
### <i class="icon-help"></i>Jenkins FAQ 
 - What is the difference between Maven and Jenkins regarding testing?
>Jenkins runs maven whenever it gets a build request. It interprets the testing, checkstyle, coverage and findbugs results and keeps a history of the output generated by maven.

 - Think something should be added to this FAQ?
>Send Martin an e-mail at `jorn.rogalla@gmail.com`

# Sonar <i class="icon-chart-bar"></i>
The **Code Analysis and Statistics** of BW4T is done by [Sonar](https://martinrogalla.com/gitlab/).
### <i class="icon-help"></i>Sonar FAQ 
 - I don't have any frequently asked questions right now. Do you have a question or think something should be added to this FAQ?

>Send Martin an e-mail at `jorn.rogalla@gmail.com`


  [1]: http://gitready.com/beginner/2009/01/11/reverting-files.html
  [2]: https://stackoverflow.com/tags/git/info/
  [3]: https://www.atlassian.com/git/tutorial/
  [4]: https://stackoverflow.com/questions/4114095/revert-to-previous-git-commit
  [5]: https://yangsu.github.io/pull-request-tutorial/
  [6]: https://www.atlassian.com/git/tutorial/git-branches#!checkout
