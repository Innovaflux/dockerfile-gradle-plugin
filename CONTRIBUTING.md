# Contributing to dockerfile-gradle-plugin

First off, thanks for taking the time to contribute! 🎉 We are excited that
you're interested in contributing to Innovaflux! Whether you're fixing a bug,
improving documentation, or adding a new feature, we'd love your help. This
document provides guidelines to ensure that your contributions are smoothly
integrated and adhere to the standards we strive for in all Innovaflux
repositories.

Following these guidelines helps to communicate that you respect the time of the
developers managing and developing this open source project.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [How to Contribute](#how-to-contribute)
   - [Reporting Issues](#reporting-issues)
   - [Submitting Pull Requests](#submitting-pull-requests)
3. [Development Guidelines](#development-guidelines)
   - [Coding Standards](#coding-standards)
   - [Commit Messages](#commit-messages)
   - [Testing](#testing)
4. [Support](#support)

---

## Code of Conduct

By participating in Innovaflux projects, you agree to abide by
our [Code of Conduct](CODE_OF_CONDUCT.md). Please read and follow it to ensure
that Innovaflux remains a welcoming environment for everyone.

## How to Contribute

### Reporting Issues

If you encounter a bug, have a question, or want to request a feature:

1. **Search existing issues**: Check if the issue has already been reported or
   discussed.
2. **Open a new issue**: If you couldn't find an existing issue, feel free to
   create a new one. Please provide as much detail as possible, including steps
   to reproduce the problem or a description of the feature you'd like to see.

### Submitting Pull Requests

We welcome your pull requests to improve Innovaflux projects. Here's how you can
contribute:

1. **Fork the repository** and clone it locally.
2. **Create a new branch** based on the main branch for your feature or bug
   fix (e.g., `feature/add-new-component` or `bugfix/fix-typo`).
3. **Make your changes**. Follow
   our [development guidelines](#development-guidelines).
4. **Commit your changes** with a descriptive commit message (
   see [Commit Messages](#commit-messages)).
5. **Push the changes** to your fork.
6. **Submit a pull request** (PR) to the main repository.

- Ensure that your PR includes a clear description of the problem or feature and
  how your changes address it.
- Reference any relevant issues in your PR description (e.g., "Closes #123").

We will review your pull request and may ask for changes before it is merged.
Please be patient and responsive to feedback.

## Development Guidelines

### Coding Standards

All contributions must adhere to the coding standards for the project. This
includes:

- **Formatting**: Follow the project's code formatting rules. Most of our
  projects use `Prettier` for JavaScript/TypeScript and `Checkstyle` for
  Java-based repositories.
- **Linting**: Ensure your code passes the project's linter (`ESLint`, `PMD`,
  etc.).
- **Type Safety**: If you're working with strongly-typed languages (like
  TypeScript or Java), ensure all types are properly defined.

Please refer to the `README.md` or other documentation in each repository for
language-specific guidelines.

### Commit Messages

Write clear, concise commit messages that describe what the commit does. Use the
following format:

```
<type>(<scope>): <description>

[optional body]
[optional footer]
```

- **Type**: One of `feat` (feature), `fix` (bug fix), `docs` (documentation), `style` (formatting), `refactor`, `test`, or `chore`.
- **Scope**: The area of the codebase affected by this change (e.g., `auth`, `ui`, `build`).
- **Description**: A brief explanation of the change (use the imperative mood, e.g., "Add logging for server errors").
- **Body**: (optional) More detailed explanation of the change.
- **Footer**: (optional) References to issues or PRs, e.g., "Closes #123".

Example:

```
feat(auth): add support for OAuth2 login

This commit introduces OAuth2 login support for third-party services. It also updates the login UI to reflect new login methods.

Closes #123
```

### Testing

- Ensure your changes are covered by unit tests.
- If applicable, add integration or end-to-end tests.
- All tests should pass locally before submitting a pull request. We use CI to run tests, but it's important to verify them locally.

## Support

If you need help or guidance, feel free to reach out to the Innovaflux Maintainers:

- **Issues**: For project-specific problems or questions, open a new issue in the relevant repository.
- **Discussions**: Join ongoing conversations or start a new topic in the [Discussions](https://github.com/Innovaflux/discussions) section.
- **Pull Requests**: You can also learn by reviewing other contributors' PRs and providing feedback.

Thank you for your contributions and support!

---

By contributing to Innovaflux, you affirm that your contributions comply with our [Code of Conduct](CODE_OF_CONDUCT.md) and licensing requirements.
