package net.lycoris_systems.advancedspellbooks.recipes;

import com.google.gson.JsonObject;
import net.lycoris_systems.advancedspellbooks.utils.RecipeSerializationUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RecipeUpgradeSpellbook extends ShapedRecipe {

    final int height, width;
    final NonNullList<Ingredient> recipeItems;
    final Ingredient upgradeItem;
    final ItemStack result;
    private final ResourceLocation id;

    public RecipeUpgradeSpellbook(ResourceLocation id, int width, int height,
                                  Ingredient upgradeItem, NonNullList<Ingredient> recipeItems, ItemStack result) {

        super(id, "", width, height, recipeItems, result);
        this.id = id;
        this.height = height;
        this.width = width;
        this.upgradeItem = upgradeItem;
        this.recipeItems = recipeItems;
        this.result = result;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container) {
        ItemStack result = this.result.copy();
        ItemStack toUpgrade = ItemStack.EMPTY;
        for(int i = 0; i < container.getContainerSize(); i++){
            ItemStack thisItem = container.getItem(i);
            if (this.upgradeItem.test(thisItem)) {
                toUpgrade = thisItem;
                break;
            }
        }
        if(toUpgrade.isEmpty()){
            return result;
        }
        if(toUpgrade.getTag()!=null) {
            result.setTag(toUpgrade.getTag().copy());
        }
        return result;
    }

    public static class Serializer implements RecipeSerializer<RecipeUpgradeSpellbook>{

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull RecipeUpgradeSpellbook fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            Map<String, Ingredient> map = RecipeSerializationUtils.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            String[] pattern = RecipeSerializationUtils.shrink(RecipeSerializationUtils.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int cols = pattern[0].length();
            int rows = pattern.length;
            NonNullList<Ingredient> ingredients = RecipeSerializationUtils.dissolvePattern(pattern, map, cols, rows);
            ItemStack itemstack = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            Ingredient upgradeFrom = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "upgradeFrom"));
            return new RecipeUpgradeSpellbook(id, cols, rows, upgradeFrom, ingredients, itemstack);
        }

        @Override
        public @Nullable RecipeUpgradeSpellbook fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
            int width = byteBuf.readVarInt();
            int height = byteBuf.readVarInt();
            int size = byteBuf.readInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(size, Ingredient.EMPTY);
            for(int i = 0; i < size; i++){
                ingredients.set(i, Ingredient.fromNetwork(byteBuf));
            }
            Ingredient upgradeItem = Ingredient.fromNetwork(byteBuf);
            ItemStack result = byteBuf.readItem();
            return new RecipeUpgradeSpellbook(id, width, height, upgradeItem, ingredients, result);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull RecipeUpgradeSpellbook recipe) {
            byteBuf.writeVarInt(recipe.width).writeVarInt(recipe.height);
            byteBuf.writeInt(recipe.recipeItems.size());
            for(Ingredient ingredient: recipe.recipeItems){
                ingredient.toNetwork(byteBuf);
            }
            recipe.upgradeItem.toNetwork(byteBuf);
            byteBuf.writeItemStack(recipe.result, false);
        }
    }
}
