# Injectable Recipes

Injectable Recipes is a lightweight API to easily register recipes with Fabric API without a single line of JSON.

For API documentation and examples see [the wiki pages](https://github.com/orangemonkey68/injectable-recipes/wiki)

## Features
- Recipe Book compatibility
- Support for all recipe types (crafting, smelting, blasting, etc)
- Automatically resolves conflicting recipes

## Future Features
- Logical Datapacks: allow players/modpack devs to overwrite specific recipes with custom datapacks
- Better recipe conflict detection
  - Right now you can't register two recipes with the same `Identifier`, but the same recipe can output two different things. In the future this will be detected and neither will be loaded.

## Version support
- Verified to be working:
  - 1.16.3
- Unverified but should work (no mappings have changed):
  - 1.16.x
- Unknown / Unsupported
  - 1.15.x or below
