# 💪 Pep Talk Generator — Git Versioning Cheatsheet

Quick reference for managing versions, tags, and changelogs.

---

## The Two Numbers in `app/build.gradle`

```kotlin
versionCode = 1        // Integer. Google Play uses this. Must always go UP. Never reset.
versionName = "1.0.0"  // String. Users see this. Format: MAJOR.MINOR.PATCH
```

### When to bump what:

| What changed?                               | Example bump       | versionCode |
|---------------------------------------------|--------------------|-------------|
| Bug fix, crash fix, small tweak             | 1.0.0 → **1.0.1** | +1          |
| New feature, new screen, new behavior       | 1.0.0 → **1.1.0** | +1          |
| Major redesign, breaking data/schema change | 1.0.0 → **2.0.0** | +1          |

> 💪 Pep Talk analogy: PATCH = new ending phrase. MINOR = new feature added. MAJOR = whole new app structure.

---

## Commit Message Format (Conventional Commits)

```
feat: add daily notification reminders
fix: crash when database returns null phrase
chore: bump compileSdk to 34
refactor: extract PepTalkCard into shared composable
docs: update README with setup instructions
test: add unit tests for pep talk generation
```

**Types and their meaning:**
- `feat` → new feature → bumps MINOR on release (1.0.0 → 1.1.0)
- `fix` → bug fix → bumps PATCH on release (1.0.0 → 1.0.1)
- `feat!` → breaking change → bumps MAJOR on release (1.0.0 → 2.0.0)
- `chore` → maintenance, no user-facing change
- `refactor` → code cleanup, behavior unchanged
- `docs` → documentation only
- `test` → tests only

---

## Tagging a Release

After bumping versions in `build.gradle` and updating `CHANGELOG.md`:

```bash
# 1. Commit the version bump
git add app/build.gradle CHANGELOG.md
git commit -m "chore: release v1.1.0"

# 2. Create an annotated tag
git tag -a v1.1.0 -m "Release 1.1.0"

# 3. Push the commit AND the tag (push alone does NOT push tags)
git push
git push origin v1.1.0
```

> ⚠️ `git push` alone silently skips tags. Always push the tag explicitly.

---

## Useful Git Commands

```bash
# List all tags
git tag

# See commits between two releases
git log v1.0.0..v1.1.0 --oneline

# Check out the code exactly as it was at a release
git checkout v1.0.0

# Return to your working branch
git checkout main
```

---

## Changelog Entry Format

Every released version block in `CHANGELOG.md` must follow this structure:

```markdown
## [X.Y.Z] - YYYY-MM-DD

> **What's new:** One or two sentences summarising what changed in plain language
> for users and future contributors.

### Added / Changed / Fixed / Removed
- bullet points describing feature/bug changes

### Phrases Added to Greeting
- "Hey champ, ..." — short description or count if many

### Phrases Added to First
- "You are the kind of person who ..." — short description or count if many

### Phrases Added to Second
- "Even on your worst day ..." — short description or count if many

### Phrases Added to Ending
- "Keep it up!" — short description or count if many
```

> If no phrases were added to a type in a given release, write `- *(none)*` so
> the section is never silently omitted.

---

## Release Checklist

- [ ] All features for this version merged and working on device
- [ ] `versionCode` incremented in `app/build.gradle`
- [ ] `versionName` updated in `app/build.gradle`
- [ ] `[Unreleased]` in `CHANGELOG.md` renamed to new version + today's date
- [ ] `> **What's new:**` summary line added to the version block
- [ ] `### Phrases Added to Greeting` section filled in (or marked `*(none)*`)
- [ ] `### Phrases Added to First` section filled in (or marked `*(none)*`)
- [ ] `### Phrases Added to Second` section filled in (or marked `*(none)*`)
- [ ] `### Phrases Added to Ending` section filled in (or marked `*(none)*`)
- [ ] Fresh empty `[Unreleased]` section (with all four Phrase subsections) added at top of `CHANGELOG.md`
- [ ] Commit made: `git commit -m "chore: release vX.Y.Z"`
- [ ] Annotated tag created: `git tag -a vX.Y.Z -m "Release X.Y.Z"`
- [ ] Tag pushed: `git push origin vX.Y.Z`
- [ ] Signed release APK or AAB generated

---

## Version History

| Version | versionCode | Date       | Notes                     |
|---------|-------------|------------|---------------------------|
| 1.1.0   | 2           | 2026-03-11 | Daily morning pep talk notification |
| 1.0.0   | 1           | 2026-03-11 | Initial build             |

> Keep this table updated each release for a quick at-a-glance history.
