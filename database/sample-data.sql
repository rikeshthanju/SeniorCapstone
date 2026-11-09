--In Kind Inventory sample inventory records
-- These are made up records for testing

INSERT INTO inventory(
    item_name,
    quantity,
    size,
    gender,
    color,
    brand,
    date_received,
    donor,
    acknowledged,
    status,
    estimated_value,
    school,
    notes
)
VALUES(
    'Basketball',
    10,
    '7',
    'Unisex',
    'Orange',
    'Spalding',
    '2026-11-01',
    'Sample Donor',
    'Y',
    'In Stock',
    100.00,
    'Sample School',
    'Sample inventory record'
),
(
    'Soccer Ball',
    5,
    '4',
    'Unisex',
    'White',
    'Adidas',
    '2026-11-02',
    'Sample Business',
    'N',
    'In Stock',
    75.00,
    'Sample School',
    'Sample inventory record'
),
(
    'Art Supplies',
    20,
    'N/A',
    'Unisex',
    'Mixed',
    'Crayola',
    '2026-11-03',
    'Sample Donor',
    'Y',
    'Given to Partner',
    150.00,
    'Sample School',
    'Sample inventory record'
);