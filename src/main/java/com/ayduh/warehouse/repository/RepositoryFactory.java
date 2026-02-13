package com.ayduh.warehouse.repository;

public final class RepositoryFactory {
    private RepositoryFactory() {}

    public static ItemRepository createItemRepository(boolean usePostgres) {
        if (usePostgres) {
            return PostgresItemsRepository.getInstance();
        }

        return InMemoryItemsRepository.getInstance();
    }

    public static CategoryRepository createCategoryRepository() {
        return CategoryRepository.getInstance();
    }
}
