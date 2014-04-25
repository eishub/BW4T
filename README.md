## Setting up Eclipse
### Download Eclipse if it's not already installed.
Download Eclipse IDE for Java Developers from [Eclipse.org](https://www.eclipse.org/downloads/)

### Installing Plugins in Eclipse
#### From the default **Kepler Update Site** download:
*Help -> Install New Software*

Search and install the following packages:

 - GMF Tooling 3.1.0
 - GMF Tooling Runtime Extensions 3.1.0

#### Add the **Groovy Update Site**:
*Help -> Install New Software -> Add...* 

Name: Groovy Update Site

Location:`http://dist.springsource.org/release/GRECLIPSE/e4.3/`

Search and install the following packages:

- Extra Groovy Compilers
- Groovy-Eclipse

#### Add the **Repast Simphony Update Site**:
*Help -> Install New Software -> Add...* 

Name: RepastSimphony

Location:`http://mirror.anl.gov/repastsimphony`

Search and install the following package:

- Repast Simphony 

## Setting up Git and Eclipse with the GitHub repository

### Download a Git client if it's not already installed.
Download a Git for you operating system. If you prefer to use a Graphical User Interface, you can check out software such as [SourceTree](http://www.sourcetreeapp.com/) from *Atlassian* or use Eclipse's built in Git client.

### Set up a SSH-key for GitHub
https://help.github.com/articles/generating-ssh-keys

### Clone the Repository

     git clone git@github.com:MartinRogalla/BW4T.git

Now open Eclipse and right-click in the package explorer area. Click *Import -> As Git Project* and choose "*Existing local repository*". Then add the directory in which you cloned the BW4T repository. Click next and choose "Import existing projects".

If everything went correctly, you shouldn't see any errors in the *Problems Dialog* (*Window -> Show View -> Problems*).

You can now simply press run, and if everything went correctly you should *BW4T* starting up.
