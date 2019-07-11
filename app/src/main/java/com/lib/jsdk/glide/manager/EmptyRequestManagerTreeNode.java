package com.lib.jsdk.glide.manager;

import androidx.annotation.NonNull;

import com.lib.jsdk.glide.RequestManager;

import java.util.Collections;
import java.util.Set;

/**
 * A {@link com.lib.jsdk.glide.manager.RequestManagerTreeNode} that returns no relatives.
 */
final class EmptyRequestManagerTreeNode implements RequestManagerTreeNode {
    @NonNull
    @Override
    public Set<RequestManager> getDescendants() {
        return Collections.emptySet();
    }
}
