package com.ryangar46.namecommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.arguments.ItemArgument;
import net.minecraft.command.arguments.ItemInput;
import net.minecraft.command.arguments.MessageArgument;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.*;

import java.util.Collection;
import java.util.List;

public class NameCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> nameCommand
                = Commands.literal("name")
                .requires((commandSource) -> commandSource.hasPermission(2))
                .then(Commands.argument("targets", EntityArgument.entities())
                    .then(Commands.argument("name", MessageArgument.message())
                        .executes((p_198493_0_) -> {
                            return nameItem(p_198493_0_.getSource(), EntityArgument.getEntities(p_198493_0_, "targets"), MessageArgument.getMessage(p_198493_0_, "name").getString());
                        })
                    )
                );
        dispatcher.register(nameCommand);
    }

    private static int nameItem(CommandSource source, Collection<? extends Entity> target, String name) throws CommandSyntaxException {
        int i = 0;

        for(Entity entity : target) {
            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                ItemStack itemstack = livingentity.getMainHandItem();
                if (!itemstack.isEmpty()) {
                    itemstack.setHoverName(ITextComponent.nullToEmpty(name));
                }
            }
        }

        return i;
    }
}
