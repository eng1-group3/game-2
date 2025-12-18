## CONTRIBUTING

**I think we should use branches to work on features** - It keeps the commit history clean, and separate.

#### Pull Request Workflow

1. Create your branch from the latest main
2. Make your changes with clear small commits
3. Open a pull request (this is a request to add your changes to the main code)
4. People can discuss in the description and comments of that PR
5. PR is merged, and its part of the main code 
6. The branch (eg. feat/add-enemies) has now served its purpose and you can return to the main branch with either: `git checkout main`, or throught he IDE environment

#### Merge vs Rebase

For this project, I recommend using merge, to preserve the full histories of developments.


#### Creating a branch

```bash
# making sure you're on main and it is up to date
git checkout main
git pull origin main

# creating the feature branch (example)
git checkout -b feat/add-enemy-sprites
```

#### Making changes

- Make your changes to the code
- Commit with descriptive messages through the IDE or code:
```bash
# select all changed files
git add .

# make a descriptive commit
git commit -m "add png files for the sprites"
```

#### Pushing the branch

```bash
git push origin feat/add-enemy-sprites
```

**Then on GitHub**:
1. Open the REPO
2. Select your feature branch using the dropdown menu above the top left corner of the file list (it will have "main" selected by default)
3. Once you've switched to your feature branch view, you'll see a yellow banner appear above the file list
4. Click "Compare & pull request" in that yellow banner
5. Fill out the title and description
6. Verify the base branch (should be "main") and compare branch (should be your feature branch)
7. Click "Create pull request"

Then the PR can be merged, or commented on, and then merged into the main code.
